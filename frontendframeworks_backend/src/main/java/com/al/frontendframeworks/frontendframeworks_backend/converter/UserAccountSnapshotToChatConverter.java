package com.al.frontendframeworks.frontendframeworks_backend.converter;

import com.al.frontendframeworks.frontendframeworks_backend.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Component
public class UserAccountSnapshotToChatConverter {

    private static final String ALL = "All";

    public UserAccountSnapshotChartDTO convertSnapshotsToLineChart(final List<UserAccountSnapshotDTO> snapshots) {
        final List<String> dateLabels = snapshots.stream()
                .map(UserAccountSnapshotDTO::getDate)
                .map(LocalDate::toString)
                .distinct()
                .collect(toList());

        final Map<String, List<Integer>> data = snapshots.stream()
                .collect(groupingBy(UserAccountSnapshotDTO::getUser))
                .entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey().getName(),
                        entry.getValue().stream().map(UserAccountSnapshotDTO::getAmount).collect(toList())))
                .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        addSummedSnapshots(data);

        final UserAccountSnapshotChartDTO snapshotLine = new UserAccountSnapshotChartDTO();
        snapshotLine.setDateLabels(dateLabels);
        snapshotLine.setData(data);
        return snapshotLine;
    }

    public UserAccountSnapshotChartDTO convertTypesToLineChart(final Map<SnapshotTypeDTO, Map<LocalDate, Integer>> groupByTypes) {
        List<String> dateLabels = groupByTypes.values().stream()
                .map(groupByDate -> new ArrayList<>(groupByDate.keySet()))
                .flatMap(Collection::stream)
                .sorted()
                .map(LocalDate::toString)
                .distinct()
                .collect(toList());

        Map<String, List<Integer>> data = groupByTypes.entrySet().stream()
                .map(typeEntry -> new AbstractMap.SimpleEntry<>(
                        typeEntry.getKey().getName(),
                        normalizeTypeValues(new ArrayList<>(typeEntry.getValue().values()), dateLabels.size())))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        final UserAccountSnapshotChartDTO typeLine = new UserAccountSnapshotChartDTO();
        typeLine.setDateLabels(dateLabels);
        typeLine.setData(data);
        return typeLine;
    }

    public UserAccountSnapshotChartDTO convertSnapshotsToBarChart(final Map<UserDTO, List<Integer>> userAmountPerYear,
                                                                  final Map<UserDTO, Map<Integer, List<UserAccountSnapshotDTO>>> groupByUserAndYear) {
        final List<String> dateLabels = groupByUserAndYear.values().stream()
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .map(String::valueOf)
                .distinct()
                .collect(toList());

        final Map<String, List<Integer>> data = userAmountPerYear.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey().getName(),
                        entry.getValue()))
                .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        addSummedSnapshots(data);

        final UserAccountSnapshotChartDTO snapshotLine = new UserAccountSnapshotChartDTO();
        snapshotLine.setDateLabels(dateLabels);
        snapshotLine.setData(data);
        return snapshotLine;
    }

    public UserAccountSnapshotPieChartDTO convertSnapshotsToPieChart(final Map<String, Integer> snapshotsSummedByType) {
        UserAccountSnapshotPieChartDTO pieChart = new UserAccountSnapshotPieChartDTO();
        pieChart.setLabels(snapshotsSummedByType.keySet());
        pieChart.setData(new ArrayList<>(snapshotsSummedByType.values()));
        return pieChart;
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

    private List<Integer> normalizeTypeValues(final List<Integer> typeValues, final int datesCount) {
        if (datesCount == typeValues.size()) {
            return typeValues;
        }

        // expected that typeValues count is less then datesCount
        int diff = datesCount - typeValues.size();
        AtomicInteger typeValuesIndex = new AtomicInteger();
        return IntStream.range(0, datesCount)
                .map(index -> index >= diff ? typeValues.get(typeValuesIndex.getAndIncrement()) : 0)
                .boxed()
                .collect(toList());
    }
}
