package com.al.frontendframeworks.frontendframeworks_backend.model;

import java.time.LocalDate;
import java.util.Objects;

public class UserAccountSnapshotDTO {

    private Integer id;
    private UserDTO user;
    private LocalDate date;
    private Integer amount;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(final UserDTO user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }
}
