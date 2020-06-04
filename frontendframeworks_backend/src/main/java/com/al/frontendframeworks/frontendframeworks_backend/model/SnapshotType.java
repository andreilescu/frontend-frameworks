package com.al.frontendframeworks.frontendframeworks_backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class SnapshotType {

    @Id
    private String id;

    @Column
    private String name;

    @OneToMany(mappedBy = "type")
    private Set<UserAccountSnapshot> snapshots;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<UserAccountSnapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(final Set<UserAccountSnapshot> snapshots) {
        this.snapshots = snapshots;
    }
}
