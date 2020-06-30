package com.al.frontendframeworks.frontendframeworks_backend.facade;

import com.al.frontendframeworks.frontendframeworks_backend.converter.UserAccountSnapshotToChatConverter;
import com.al.frontendframeworks.frontendframeworks_backend.model.*;
import com.al.frontendframeworks.frontendframeworks_backend.repository.SnapshotTypeRepository;
import com.al.frontendframeworks.frontendframeworks_backend.repository.UserAccountSnapshotRepository;
import com.al.frontendframeworks.frontendframeworks_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.Collectors.*;
import static java.util.stream.StreamSupport.stream;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Service
public class UserAccountSnapshotFacade extends AbstractFacade {

    @Autowired
    private UserAccountSnapshotRepository userAccountSnapshotRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SnapshotTypeRepository snapshotTypeRepository;
    @Autowired
    private UserAccountSnapshotToChatConverter converter;

    public void addUserAccountSnapshot(final UserAccountSnapshotRequestDTO request) {
        userRepository.findById(request.getUserId())
                .map(user -> {
                    saveUserAccountSnapshot(request, user);
                    return user;
                })
                .orElseThrow(() -> new IllegalArgumentException(String.format("User not found for %s", request.getUserId())));
    }

    public void addUserAccountSnapshots(final List<UserAccountSnapshotRequestDTO> request) {
        request.stream()
                .collect(groupingBy(UserAccountSnapshotRequestDTO::getUserId))
                .forEach((userId, snapshotsByUserId) ->
                        userRepository.findById(userId)
                                .map(user -> {
                                    snapshotsByUserId.forEach(accountRequest -> saveUserAccountSnapshot(accountRequest, user));
                                    return user;
                                })
                                .orElseThrow(() -> new IllegalArgumentException(String.format("User not found for %s", userId))));
    }

    public List<UserAccountSnapshotDTO> getAllByUserId(final Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(String.format("User not found for %s", userId)));
        return getAll().stream().filter(snapshot -> userId.equals(snapshot.getUser().getId())).collect(toList());
    }

    public List<UserAccountSnapshotDTO> getAll() {
        return stream(spliteratorUnknownSize(userAccountSnapshotRepository.findAll().iterator(), ORDERED), false)
                .map(snapshot -> getMapper(UserAccountSnapshot.class, UserAccountSnapshotDTO.class)
                        .map(snapshot, UserAccountSnapshotDTO.class))
                .collect(toList());
    }

    public UserAccountSnapshotChartDTO getAllSummedByAssertsAsLineChart() {
        return converter.convertSnapshotsToLineChart(getAllSummedByAsserts());
    }

    public UserAccountSnapshotChartDTO getAllSummedByAssertsIncreaseByMonthGroupByYearAsLineChart() {
        Map<UserDTO, List<UserAccountSnapshotDTO>> sortedSummedAsserts = getAllSummedByAsserts().stream()
                .sorted(Comparator.comparing(UserAccountSnapshotDTO::getDate))
                .collect(groupingBy(UserAccountSnapshotDTO::getUser));
        List<UserAccountSnapshotDTO> increasedByMonth = sortedSummedAsserts.entrySet().stream()
                .peek(entry -> {
                    AtomicInteger index = new AtomicInteger();
                    entry.getValue().forEach(snapshot -> {
                        int nextIndex = index.incrementAndGet();
                        int monthIncrease = nextIndex <= entry.getValue().size() - 1
                                ? entry.getValue().get(nextIndex).getAmount() - snapshot.getAmount()
                                : 0;
                        snapshot.setAmount(monthIncrease);
                    });
                })
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(toList());
        return converter.convertSnapshotsToLineChart(increasedByMonth);
    }

    public UserAccountSnapshotChartDTO getAllSummedByAssertsGroupByYearAsBarChart() {
        Map<UserDTO, Map<Integer, List<UserAccountSnapshotDTO>>> groupByUserAndYear = getAllSummedByAsserts().stream()
                .collect(groupingBy(UserAccountSnapshotDTO::getUser, groupingBy(s -> s.getDate().getYear())));

        Map<UserDTO, List<Integer>> userAmountPerYear = new HashMap<>();
        groupByUserAndYear.forEach((groupByUser, userValues) -> userValues.forEach((groupByYear, yearValues) -> {

            List<UserAccountSnapshotDTO> yearSnapshots = yearValues.stream()
                    .sorted(Comparator.comparing(UserAccountSnapshotDTO::getDate))
                    .collect(toList());

            Optional<Integer> firstSnapshotOfYear = yearSnapshots.stream().map(UserAccountSnapshotDTO::getAmount)
                    .findFirst();
            Optional<Integer> lastSnapshotOfYear = yearSnapshots.stream().map(UserAccountSnapshotDTO::getAmount)
                    .reduce((first, second) -> second);

            int yearAmount = 0;
            if (firstSnapshotOfYear.isPresent() && lastSnapshotOfYear.isPresent()) {
                yearAmount = lastSnapshotOfYear.get() - firstSnapshotOfYear.get();
            }

            if (userAmountPerYear.containsKey(groupByUser)) {
                List<Integer> initialAmoutByYear = isNotEmpty(userAmountPerYear.get(groupByUser))
                        ? userAmountPerYear.get(groupByUser)
                        : new ArrayList<>();
                List<Integer> amountByYear = new ArrayList<>(initialAmoutByYear);
                amountByYear.add(yearAmount);
                userAmountPerYear.put(groupByUser, amountByYear);
            } else {
                List<Integer> amountByYear = new ArrayList<>();
                amountByYear.add(yearAmount);
                userAmountPerYear.put(groupByUser, amountByYear);
            }
        }));

        return converter.convertSnapshotsToBarChart(userAmountPerYear, groupByUserAndYear);
    }

    public List<UserAccountSnapshotDTO> getAllSummedByAsserts() {
        return getGroupByUserAndDate().values().stream()
                .map(values -> values.values().stream()
                        .map(snapshots -> {
                            final Integer summedAmount = snapshots
                                    .stream()
                                    .mapToInt(UserAccountSnapshotDTO::getAmount)
                                    .sum();
                            return snapshots
                                    .stream()
                                    .peek(account -> account.setAmount(summedAmount))
                                    .findFirst();
                        })
                        .collect(toList())
                )
                .flatMap(Collection::stream)
                .map(Optional::get)
                .sorted(Comparator.comparing(UserAccountSnapshotDTO::getDate))
                .collect(toList());
    }

    public UserAccountSnapshotPieChartDTO getLatestAssertsAsPie() {
        // get latest months from snapshot
        LocalDate monthFromLatestSnapshot = getAll().stream()
                .map(UserAccountSnapshotDTO::getDate)
                .max(Comparator.naturalOrder())
                .orElse(LocalDate.now());

        Map<SnapshotTypeDTO, List<UserAccountSnapshotDTO>> lastMonthSnapshotsGroupedByType = getAll().stream()
                .filter(snapshot -> monthFromLatestSnapshot.equals(snapshot.getDate()))
                .collect(groupingBy(UserAccountSnapshotDTO::getType));

        Map<String, Integer> lastMonthSnapshotsSummedByType = lastMonthSnapshotsGroupedByType.entrySet().stream()
                .map(entry -> {
                    String key = entry.getKey().getName();
                    Integer value = entry.getValue().stream()
                            .map(UserAccountSnapshotDTO::getAmount)
                            .reduce(0, Integer::sum);
                    return new AbstractMap.SimpleEntry<>(key, value);
                })
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        return converter.convertSnapshotsToPieChart(lastMonthSnapshotsSummedByType);
    }

    public UserAccountSnapshotChartDTO getMonthlyGrowthByAssertsAsLineChart() {
        Map<SnapshotTypeDTO, Map<LocalDate, List<UserAccountSnapshotDTO>>> groupByTypeAndDate = getAll().stream()
                .collect(groupingBy(UserAccountSnapshotDTO::getType, groupingBy(UserAccountSnapshotDTO::getDate)));
        Map<SnapshotTypeDTO, Map<LocalDate, Integer>> monthlyGrowthByType = groupByTypeAndDate.entrySet().stream()
                .map(typeEntry -> {
                    Map<LocalDate, Integer> entriesSumByDate = typeEntry.getValue().entrySet().stream()
                            .map(dateEntry -> new AbstractMap.SimpleEntry<>(dateEntry.getKey(),
                                    dateEntry.getValue().stream()
                                            .map(UserAccountSnapshotDTO::getAmount)
                                            .reduce(0, Integer::sum)))
                            .sorted(Map.Entry.comparingByKey())
                            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                    return new AbstractMap.SimpleEntry<>(typeEntry.getKey(), entriesSumByDate);
                })
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return converter.convertTypesToLineChart(monthlyGrowthByType);
    }

    public void deleteAll() {
        userAccountSnapshotRepository.deleteAll();
    }

    private Map<UserDTO, Map<LocalDate, List<UserAccountSnapshotDTO>>> getGroupByUserAndDate() {
        return getAll().stream()
                .collect(groupingBy(UserAccountSnapshotDTO::getUser, groupingBy(UserAccountSnapshotDTO::getDate)));
    }

    private void saveUserAccountSnapshot(final UserAccountSnapshotRequestDTO request, final User user) {
        UserAccountSnapshot accountSnapshot = new UserAccountSnapshot();
        accountSnapshot.setUser(user);
        // '2011-12-03 - 'YYYY-MM-dd''
        accountSnapshot.setDate(LocalDate.parse(request.getDate()));
        accountSnapshot.setAmount(request.getAmount());
        snapshotTypeRepository.findById(request.getType())
                .ifPresent(accountSnapshot::setType);
        userAccountSnapshotRepository.save(accountSnapshot);
    }
}
