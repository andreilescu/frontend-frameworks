package com.al.frontendframeworks.frontendframeworks_backend.facade;

import com.al.frontendframeworks.frontendframeworks_backend.model.SnapshotType;
import com.al.frontendframeworks.frontendframeworks_backend.model.SnapshotTypeDTO;
import com.al.frontendframeworks.frontendframeworks_backend.repository.SnapshotTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SnapshotTypeFacade {

    @Autowired
    private SnapshotTypeRepository snapshotTypeRepository;

    public void saveSnapshotType(final SnapshotTypeDTO request) {
        SnapshotType snapshotType = new SnapshotType();
        snapshotType.setId(request.getId());
        snapshotType.setName(request.getName());
        snapshotTypeRepository.save(snapshotType);
    }
}
