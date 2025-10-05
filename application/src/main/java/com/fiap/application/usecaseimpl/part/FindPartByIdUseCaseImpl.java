package com.fiap.application.usecaseimpl.part;

import com.fiap.application.gateway.part.PartGateway;
import com.fiap.core.domain.part.Part;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.part.FindPartByIdUseCase;
import java.util.UUID;

public class FindPartByIdUseCaseImpl implements FindPartByIdUseCase {

    private final PartGateway partGateway;

    public FindPartByIdUseCaseImpl(PartGateway partGateway) {
        this.partGateway = partGateway;
    }

    @Override
    public Part execute(UUID id) throws NotFoundException {
        return partGateway.findById(id)
                .orElseThrow(() -> new NotFoundException("Part com ID " + id + " não encontrada.", "PART-404"));
    }
}