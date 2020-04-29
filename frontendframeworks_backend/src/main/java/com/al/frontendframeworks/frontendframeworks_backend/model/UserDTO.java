package com.al.frontendframeworks.frontendframeworks_backend.model;

import java.util.Objects;

public class UserDTO {

    private Integer id;
    private String name;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id.equals(userDTO.id) &&
                Objects.equals(name, userDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
