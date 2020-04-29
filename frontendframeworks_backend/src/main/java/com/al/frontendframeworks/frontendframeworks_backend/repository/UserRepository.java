package com.al.frontendframeworks.frontendframeworks_backend.repository;

import com.al.frontendframeworks.frontendframeworks_backend.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
