package com.fiap.application.usecaseimpl.part;

import com.fiap.application.gateway.part.PartGateway;
import com.fiap.application.gateway.part.WorkOrderPartGateway;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.part.DeletePartUseCase;

import java.util.UUID;

public class DeletePartUseCaseImpl implements DeletePartUseCase {

    private final PartGateway partGateway;
    private final WorkOrderPartGateway workOrderPartGateway;

    public DeletePartUseCaseImpl(PartGateway partGateway, WorkOrderPartGateway workOrderPartGateway) {
        this.partGateway = partGateway;
        this.workOrderPartGateway = workOrderPartGateway;
    }

    @Override
    public void execute(UUID id) throws NotFoundException, BusinessRuleException {
        if (!partGateway.existsById(id)) {
            throw new NotFoundException("Part with ID " + id + " not found.", "PART-404");
        }

        if (workOrderPartGateway.existsByPartId(id)) {
            throw new BusinessRuleException("Cannot delete part with ID " + id + " because it is associated with one or more work orders.", "PART-DELETE-CONFLICT");
        }

        partGateway.delete(id);
    }
}