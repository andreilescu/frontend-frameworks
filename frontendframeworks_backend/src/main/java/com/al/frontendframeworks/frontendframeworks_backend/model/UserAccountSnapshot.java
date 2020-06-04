package com.al.frontendframeworks.frontendframeworks_backend.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class UserAccountSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "snapshotType_id", nullable = false)
    private SnapshotType type;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer amount;

    public UserAccountSnapshot() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public SnapshotType getType() {
        return type;
    }

    public void setType(final SnapshotType type) {
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
}
