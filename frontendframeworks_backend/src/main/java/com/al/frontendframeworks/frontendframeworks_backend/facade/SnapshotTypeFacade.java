package com.al.frontendframeworks.frontendframeworks_backend.facade;

import com.al.frontendframeworks.frontendframeworks_backend.model.SnapshotType;
import com.al.frontendframeworks.frontendframeworks_backend.model.SnapshotTypeDTO;
import com.al.frontendframeworks.frontendframeworks_backend.repository.SnapshotTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

@Component
public class SnapshotTypeFacade extends AbstractFacade {

    @Autowired
    private SnapshotTypeRepository snapshotTypeRepository;

    public void saveSnapshotType(final SnapshotTypeDTO request) {
        SnapshotType snapshotType = new SnapshotType();
        snapshotType.setId(request.getId());
        snapshotType.setName(request.getName());
        snapshotTypeRepository.save(snapshotType);
    }

    public void deleteAll() {
        snapshotTypeRepository.deleteAll();
    }

    public void deleteById(final String id) {
        snapshotTypeRepository.deleteById(id);
    }

    public List<SnapshotTypeDTO> getAll() {
        return stream(spliteratorUnknownSize(snapshotTypeRepository.findAll().iterator(), ORDERED), false)
                .map(type -> getMapper(SnapshotType.class, SnapshotTypeDTO.class).map(type, SnapshotTypeDTO.class))
                .collect(Collectors.toList());
    }
}
