package com.fiap.application.usecaseimpl.part;

import com.fiap.application.gateway.part.PartGateway;
import com.fiap.core.domain.part.Part;
import com.fiap.usecase.part.FindPartsByIdsUseCase;
import java.util.List;
import java.util.UUID;

public class FindPartsByIdsUseCaseImpl implements FindPartsByIdsUseCase {

    private final PartGateway partGateway;

    public FindPartsByIdsUseCaseImpl(PartGateway partGateway) {
        this.partGateway = partGateway;
    }

    @Override
    public List<Part> execute(List<UUID> ids) {
        return partGateway.findByIds(ids);
    }
}