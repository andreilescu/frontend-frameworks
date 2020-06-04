package com.al.frontendframeworks.frontendframeworks_backend.model;

import java.util.List;
import java.util.Set;

public class UserAccountSnapshotPieChartDTO {
    private Set<String> labels;
    private List<Integer> data;

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(final Set<String> labels) {
        this.labels = labels;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(final List<Integer> data) {
        this.data = data;
    }
}
