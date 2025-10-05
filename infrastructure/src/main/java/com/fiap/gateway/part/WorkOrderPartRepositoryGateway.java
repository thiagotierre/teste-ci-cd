package com.fiap.gateway.part;

import com.fiap.application.gateway.part.WorkOrderPartGateway;
import com.fiap.persistence.repository.part.WorkOrderPartEntityRepository;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class WorkOrderPartRepositoryGateway implements WorkOrderPartGateway {

    private final WorkOrderPartEntityRepository repository;

    public WorkOrderPartRepositoryGateway(WorkOrderPartEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsByPartId(UUID partId) {
        return repository.existsByPartId(partId);
    }
}