package com.fiap.application.usecaseimpl.part;

import com.fiap.application.gateway.part.PartGateway;
import com.fiap.core.domain.part.Part;
import com.fiap.usecase.part.CreatePartUseCase;

public class CreatePartUseCaseImpl implements CreatePartUseCase {

    private final PartGateway partGateway;

    public CreatePartUseCaseImpl(PartGateway partGateway) {
        this.partGateway = partGateway;
    }

    @Override
    public Part execute(Part part) {
        return partGateway.create(part);
    }
}