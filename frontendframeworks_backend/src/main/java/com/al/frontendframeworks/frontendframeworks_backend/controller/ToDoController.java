package com.al.frontendframeworks.frontendframeworks_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToDoController {

    @GetMapping("/rest/todos")
    public String getTodo() {
        return "all to dos as one";
    }
}
