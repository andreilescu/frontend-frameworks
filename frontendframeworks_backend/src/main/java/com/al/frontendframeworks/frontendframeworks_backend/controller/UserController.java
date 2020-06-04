package com.al.frontendframeworks.frontendframeworks_backend.controller;

import com.al.frontendframeworks.frontendframeworks_backend.facade.UserFacade;
import com.al.frontendframeworks.frontendframeworks_backend.model.User;
import com.al.frontendframeworks.frontendframeworks_backend.model.UserDTO;
import com.al.frontendframeworks.frontendframeworks_backend.model.UserRequestDTO;
import com.al.frontendframeworks.frontendframeworks_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Controller
@RequestMapping(path = "/users")
public class UserController extends AbstractController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFacade userFacade;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody final UserRequestDTO request) {
        userFacade.saveUser(request);
    }

    @PostMapping("/quickAdd")
    @ResponseStatus(HttpStatus.CREATED)
    public void quickAddUsers(@RequestBody final List<UserRequestDTO> request) {
        request.forEach(userFacade::saveUser);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable final Integer userId) {
        userRepository.deleteById(userId);
    }

    @GetMapping
    @ResponseBody
    public List<UserDTO> getAllUser() {
        return stream(spliteratorUnknownSize(userRepository.findAll().iterator(), ORDERED), false)
                .map(user -> getMapper(User.class, UserDTO.class)
                        .map(user, UserDTO.class))
                .collect(toList());
    }
}
