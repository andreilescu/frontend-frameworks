package com.al.frontendframeworks.frontendframeworks_backend.model;

import java.util.List;
import java.util.Map;

public class UserAccountSnapshotChartDTO {
    private List<String> dateLabels;
    private Map<String, List<Integer>> data;

    public List<String> getDateLabels() {
        return dateLabels;
    }

    public void setDateLabels(List<String> dateLabels) {
        this.dateLabels = dateLabels;
    }

    public Map<String, List<Integer>> getData() {
        return data;
    }

    public void setData(Map<String, List<Integer>> data) {
        this.data = data;
    }
}
