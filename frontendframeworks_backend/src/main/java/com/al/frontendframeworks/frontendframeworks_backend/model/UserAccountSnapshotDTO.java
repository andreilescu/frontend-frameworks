package com.al.frontendframeworks.frontendframeworks_backend.model;

import java.time.LocalDate;
import java.util.Objects;

public class UserAccountSnapshotDTO {

    private Integer id;
    private UserDTO user;
    private SnapshotTypeDTO type;
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

    public SnapshotTypeDTO getType() {
        return type;
    }

    public void setType(final SnapshotTypeDTO type) {
        this.type = type;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccountSnapshotDTO that = (UserAccountSnapshotDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
