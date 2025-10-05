package com.fiap.challenge.parts.useCases.find;

import com.fiap.challenge.parts.entity.PartModel;

import java.util.UUID;

public interface FindPartModelByIdUseCase {

    public PartModel execute(UUID id);
}
