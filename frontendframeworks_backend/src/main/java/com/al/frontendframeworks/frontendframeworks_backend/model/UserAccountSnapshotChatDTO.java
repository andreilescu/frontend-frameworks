package com.al.frontendframeworks.frontendframeworks_backend.model;

import java.util.List;
import java.util.Map;

public class UserAccountSnapshotChatDTO {
    private List<String> dateLabels;
    private Map<String, List<Integer>> snapshotsByUser;

    public List<String> getDateLabels() {
        return dateLabels;
    }

    public void setDateLabels(List<String> dateLabels) {
        this.dateLabels = dateLabels;
    }

    public Map<String, List<Integer>> getSnapshotsByUser() {
        return snapshotsByUser;
    }

    public void setSnapshotsByUser(Map<String, List<Integer>> snapshotsByUser) {
        this.snapshotsByUser = snapshotsByUser;
    }
}
