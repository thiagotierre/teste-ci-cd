package com.fiap.challenge.parts.useCases.find;

import java.util.UUID;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.shared.model.ResponseApi;

public interface FindPartByIdUseCase {
    ResponseApi<PartResponseDTO> execute(UUID id);
}