package com.al.frontendframeworks.frontendframeworks_backend.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user")
    private Set<UserAccountSnapshot> accountSnapshots;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<UserAccountSnapshot> getAccountSnapshots() {
        return accountSnapshots;
    }

    public void setAccountSnapshots(final Set<UserAccountSnapshot> accountSnapshots) {
        this.accountSnapshots = accountSnapshots;
    }
}
