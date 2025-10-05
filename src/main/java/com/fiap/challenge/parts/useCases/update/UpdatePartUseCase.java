package com.fiap.challenge.parts.useCases.update;

import java.util.UUID;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.dto.UpdatePartRequestDTO;
import com.fiap.challenge.shared.model.ResponseApi;

public interface UpdatePartUseCase {
    ResponseApi<PartResponseDTO> execute(UUID id, UpdatePartRequestDTO partRequest);
}	