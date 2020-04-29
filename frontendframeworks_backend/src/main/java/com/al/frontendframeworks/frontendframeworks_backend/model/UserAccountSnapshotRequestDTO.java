package com.al.frontendframeworks.frontendframeworks_backend.model;

import java.util.Date;

public class UserAccountSnapshotRequestDTO {

    private Integer id;
    private Integer userId;
    private String date;
    private Integer amount;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }
}
