package com.al.frontendframeworks.frontendframeworks_backend.controller;

import com.al.frontendframeworks.frontendframeworks_backend.model.*;
import com.al.frontendframeworks.frontendframeworks_backend.repository.UserAccountSnapshotRepository;
import com.al.frontendframeworks.frontendframeworks_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Controller
@RequestMapping(path = "/accountSnapshots")
public class UserAccountSnapshotController extends AbstractController {
    @Autowired
    private UserAccountSnapshotRepository userAccountSnapshotRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserAccountSnapshot(@RequestBody UserAccountSnapshotRequestDTO request) {
        userRepository.findById(request.getUserId())
                .map(user -> {
                    saveUserAccountSnapshot(request, user);
                    return user;
                })
                .orElseThrow(() -> new IllegalArgumentException(String.format("User not found for %s", request.getUserId())));
    }

    private void saveUserAccountSnapshot(@RequestBody final UserAccountSnapshotRequestDTO request, final User user) {
        UserAccountSnapshot accountSnapshot = new UserAccountSnapshot();
        accountSnapshot.setUser(user);
        // '2011-12-03 - 'YYYY-MM-dd''
        accountSnapshot.setDate(LocalDate.parse(request.getDate()));
        accountSnapshot.setAmount(request.getAmount());
        userAccountSnapshotRepository.save(accountSnapshot);
    }

    @PostMapping("/quickAdd")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserAccountSnapshots(@RequestBody List<UserAccountSnapshotRequestDTO> request) {

        final Map<Integer, List<UserAccountSnapshotRequestDTO>> requestGroupByUserIds = request.stream()
                .collect(groupingBy(UserAccountSnapshotRequestDTO::getUserId));
        requestGroupByUserIds.forEach((userId, snapshotsByUserId) ->
                userRepository.findById(userId)
                        .map(user -> {
                            snapshotsByUserId.forEach(accountRequest -> saveUserAccountSnapshot(accountRequest, user));
                            return user;
                        })
                        .orElseThrow(() -> new IllegalArgumentException(String.format("User not found for %s", userId))));
    }

    @GetMapping("/users/{userId}")
    @ResponseBody
    public List<UserAccountSnapshotDTO> getAllUserAccountSnapshotsByUserId(@PathVariable final Integer userId) {

        return userRepository.findById(userId)
                .map(user -> mapUserAccountSnapshots())
                .orElseThrow(() -> new IllegalArgumentException(String.format("User not found for %s", userId)));
    }

    @GetMapping
    @ResponseBody
    public List<UserAccountSnapshotDTO> getAllUserAccountSnapshots() {
        return mapUserAccountSnapshots();
    }

    private List<UserAccountSnapshotDTO> mapUserAccountSnapshots() {
        return stream(spliteratorUnknownSize(userAccountSnapshotRepository.findAll().iterator(), ORDERED), false)
                .map(snapshot -> getMapper(UserAccountSnapshot.class, UserAccountSnapshotDTO.class)
                        .map(snapshot, UserAccountSnapshotDTO.class))
                .collect(toList());
    }

    @GetMapping("/summedByDate")
    @ResponseBody
    public List<UserAccountSnapshotDTO> getAllUserAccountSnapshotsSummedByDate() {

        final List<UserAccountSnapshotDTO> unstructuredUserAccountSnapshots = mapUserAccountSnapshots();
        final Map<UserDTO, Map<LocalDate, List<UserAccountSnapshotDTO>>> groupByUserAndDate = unstructuredUserAccountSnapshots.stream()
                .collect(groupingBy(UserAccountSnapshotDTO::getUser, groupingBy(UserAccountSnapshotDTO::getDate)));

        return groupByUserAndDate.values()
                .stream()
                .map(localDateListMap -> localDateListMap.values()
                        .stream()
                        .map(userAccountSnapshotDTOS -> {
                            final Integer summedAmount = userAccountSnapshotDTOS
                                    .stream()
                                    .mapToInt(UserAccountSnapshotDTO::getAmount)
                                    .sum();
                            return userAccountSnapshotDTOS
                                    .stream()
                                    .peek(account -> account.setAmount(summedAmount))
                                    .findFirst();
                        })
                        .collect(toList())
                )
                .flatMap(Collection::stream)
                .map(Optional::get)
                .sorted(Comparator.comparing(UserAccountSnapshotDTO::getDate))
                .collect(toList());
    }
}
