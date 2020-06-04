package com.al.frontendframeworks.frontendframeworks_backend.controller;

import com.al.frontendframeworks.frontendframeworks_backend.facade.SnapshotTypeFacade;
import com.al.frontendframeworks.frontendframeworks_backend.model.SnapshotType;
import com.al.frontendframeworks.frontendframeworks_backend.model.SnapshotTypeDTO;
import com.al.frontendframeworks.frontendframeworks_backend.repository.SnapshotTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

@Controller
@RequestMapping(path = "/snapshotTypes")
public class SnapshotTypeController extends AbstractController {

    @Autowired
    private SnapshotTypeFacade snapshotTypeFacade;
    @Autowired
    private SnapshotTypeRepository snapshotTypeRepository;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSnapshotType(@RequestBody SnapshotTypeDTO request) {
        snapshotTypeFacade.saveSnapshotType(request);
    }

    @PostMapping("/quickAdd")
    @ResponseStatus(HttpStatus.CREATED)
    public void quickAddSnapshotTypes(@RequestBody List<SnapshotTypeDTO> request) {
        request.forEach(snapshotTypeFacade::saveSnapshotType);
    }

    @GetMapping
    @ResponseBody
    public List<SnapshotTypeDTO> getSnapshotTypes() {
        return stream(spliteratorUnknownSize(snapshotTypeRepository.findAll().iterator(), ORDERED), false)
                .map(type -> getMapper(SnapshotType.class, SnapshotTypeDTO.class).map(type, SnapshotTypeDTO.class))
                .collect(Collectors.toList());
    }

}
