package com.al.frontendframeworks.frontendframeworks_backend.facade;

import com.al.frontendframeworks.frontendframeworks_backend.model.User;
import com.al.frontendframeworks.frontendframeworks_backend.model.UserRequestDTO;
import com.al.frontendframeworks.frontendframeworks_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(final UserRequestDTO request) {
        User user = new User();
        user.setName(request.getName());
        userRepository.save(user);
    }
}
