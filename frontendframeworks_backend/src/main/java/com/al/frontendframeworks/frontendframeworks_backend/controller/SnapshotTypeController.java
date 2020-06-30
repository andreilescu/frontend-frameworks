package com.al.frontendframeworks.frontendframeworks_backend.controller;

import com.al.frontendframeworks.frontendframeworks_backend.facade.SnapshotTypeFacade;
import com.al.frontendframeworks.frontendframeworks_backend.model.SnapshotTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/snapshotTypes")
public class SnapshotTypeController extends AbstractController {

    @Autowired
    private SnapshotTypeFacade snapshotTypeFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSnapshotType(@RequestBody SnapshotTypeDTO request) {
        snapshotTypeFacade.saveSnapshotType(request);
    }

    @PostMapping("/quickAdd")
    @ResponseStatus(HttpStatus.CREATED)
    public void quickAddSnapshotTypes(@RequestBody List<SnapshotTypeDTO> request) {
        request.forEach(snapshotTypeFacade::saveSnapshotType);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateSnapshotType(@RequestBody SnapshotTypeDTO request) {
        snapshotTypeFacade.saveSnapshotType(request);
    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
        snapshotTypeFacade.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable final String id) {
        snapshotTypeFacade.deleteById(id);
    }

    @GetMapping
    @ResponseBody
    public List<SnapshotTypeDTO> getAll() {
        return snapshotTypeFacade.getAll();
    }

}
