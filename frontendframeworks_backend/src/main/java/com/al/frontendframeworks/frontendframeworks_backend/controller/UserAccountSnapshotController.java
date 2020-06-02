package com.al.frontendframeworks.frontendframeworks_backend.controller;

import com.al.frontendframeworks.frontendframeworks_backend.facade.UserAccountSnapshotFacade;
import com.al.frontendframeworks.frontendframeworks_backend.model.UserAccountSnapshotDTO;
import com.al.frontendframeworks.frontendframeworks_backend.model.UserAccountSnapshotChatDTO;
import com.al.frontendframeworks.frontendframeworks_backend.model.UserAccountSnapshotRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/accountSnapshots")
public class UserAccountSnapshotController extends AbstractController {
    @Autowired
    private UserAccountSnapshotFacade userAccountSnapshotFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserAccountSnapshot(@RequestBody UserAccountSnapshotRequestDTO request) {
        userAccountSnapshotFacade.addUserAccountSnapshot(request);
    }

    @PostMapping("/quickAdd")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserAccountSnapshots(@RequestBody List<UserAccountSnapshotRequestDTO> request) {
        userAccountSnapshotFacade.addUserAccountSnapshots(request);
    }

    @GetMapping("/users/{userId}")
    @ResponseBody
    public List<UserAccountSnapshotDTO> getAllByUserId(@PathVariable final Integer userId) {
        return userAccountSnapshotFacade.getAllByUserId(userId);
    }

    @GetMapping
    @ResponseBody
    public List<UserAccountSnapshotDTO> getAll() {
        return userAccountSnapshotFacade.getAll();
    }

    @GetMapping("/summedByAsserts")
    @ResponseBody
    public List<UserAccountSnapshotDTO> getAllSummedByAsserts() {
        return userAccountSnapshotFacade.getAllSummedByAsserts();
    }

    @GetMapping("/asLine")
    @ResponseBody
    public UserAccountSnapshotChatDTO getAllSummedByAssertsAsLineChart() {
        return userAccountSnapshotFacade.getAllSummedByAssertsAsLineChart();
    }

    @GetMapping("/years")
    @ResponseBody
    public UserAccountSnapshotChatDTO getAllSummedByAssertsGroupByYearAsBarChart() {
        return userAccountSnapshotFacade.getAllSummedByAssertsGroupByYearAsBarChart();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllUserAccountSnapshots() {
        userAccountSnapshotFacade.deleteAll();
    }
}
