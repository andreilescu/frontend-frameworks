package com.al.frontendframeworks.frontendframeworks_backend.facade;

import com.al.frontendframeworks.frontendframeworks_backend.converter.UserAccountSnapshotToChatConvertor;
import com.al.frontendframeworks.frontendframeworks_backend.model.*;
import com.al.frontendframeworks.frontendframeworks_backend.repository.UserAccountSnapshotRepository;
import com.al.frontendframeworks.frontendframeworks_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Service
public class UserAccountSnapshotFacade extends AbstractFacade {

    @Autowired
    private UserAccountSnapshotRepository userAccountSnapshotRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAccountSnapshotToChatConvertor convertor;

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

    public UserAccountSnapshotChatDTO getAllSummedByAssertsFormatAsLineChat() {
        final List<UserAccountSnapshotDTO> allSummedByAsserts = getAllSummedByAsserts();
        return convertor.convertSnapshotToLineChar(allSummedByAsserts);
    }

    public UserAccountSnapshotChatDTO getAllSummedByAssertsGroupByYearAsBarChat() {
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

        return convertor.convertSnapshotToBarChar(userAmountPerYear, groupByUserAndYear);
    }

    public List<UserAccountSnapshotDTO> getAllSummedByAsserts() {
        return getGroupByUserAndDate().values().stream()
                .map(values -> values.values()
                        .stream()
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
        userAccountSnapshotRepository.save(accountSnapshot);
    }
}
