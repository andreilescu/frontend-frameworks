package com.al.frontendframeworks.frontendframeworks_backend.converter;

import com.al.frontendframeworks.frontendframeworks_backend.model.UserAccountSnapshotChatDTO;
import com.al.frontendframeworks.frontendframeworks_backend.model.UserAccountSnapshotDTO;
import com.al.frontendframeworks.frontendframeworks_backend.model.UserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Component
public class UserAccountSnapshotToChatConvertor {

    private static final String ALL = "All";

    public UserAccountSnapshotChatDTO convertSnapshotToLineChar(final List<UserAccountSnapshotDTO> snapshots) {
        final Map<String, List<Integer>> simplifiedSnapshots = snapshots.stream()
                .collect(groupingBy(UserAccountSnapshotDTO::getUser))
                .entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey().getName(),
                        entry.getValue().stream().map(UserAccountSnapshotDTO::getAmount).collect(toList())))
                .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
        addSummedSnapshots(simplifiedSnapshots);

        final List<String> dateLabels = snapshots.stream()
                .map(UserAccountSnapshotDTO::getDate)
                .map(LocalDate::toString)
                .distinct()
                .collect(toList());

        final UserAccountSnapshotChatDTO snapshotLine = new UserAccountSnapshotChatDTO();
        snapshotLine.setDateLabels(dateLabels);
        snapshotLine.setSnapshotsByUser(simplifiedSnapshots);
        return snapshotLine;
    }

    public UserAccountSnapshotChatDTO convertSnapshotToBarChar(final Map<UserDTO, List<Integer>> userAmountPerYear,
                                                               final Map<UserDTO, Map<Integer, List<UserAccountSnapshotDTO>>> groupByUserAndYear) {
        final Map<String, List<Integer>> simplifiedSnapshots = userAmountPerYear.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey().getName(),
                        entry.getValue()))
                .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
        addSummedSnapshots(simplifiedSnapshots);

        final List<String> dateLabels = groupByUserAndYear.values()
                .stream()
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .map(String::valueOf)
                .distinct()
                .collect(toList());

        final UserAccountSnapshotChatDTO snapshotLine = new UserAccountSnapshotChatDTO();
        snapshotLine.setDateLabels(dateLabels);
        snapshotLine.setSnapshotsByUser(simplifiedSnapshots);
        return snapshotLine;
    }

    private void addSummedSnapshots(final Map<String, List<Integer>> simpliefiedSnapshots) {
        if (isNotEmpty(simpliefiedSnapshots)) {
            AtomicInteger index = new AtomicInteger();
            List<Integer> summedSnapshots = new ArrayList<>();
            final int numberOfIterations = simpliefiedSnapshots.values().stream().findFirst().map(List::size).orElse(0);
            for (int i = 0; i < numberOfIterations; i++) {
                int currentIndex = index.getAndIncrement();
                summedSnapshots.add(simpliefiedSnapshots.values().stream()
                        .map(list -> list.get(currentIndex))
                        .reduce(0, Integer::sum));
            }
            simpliefiedSnapshots.put(ALL, summedSnapshots);
        }
    }


}
