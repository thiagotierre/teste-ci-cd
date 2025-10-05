package com.fiap.challenge.parts.useCases.find;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.shared.model.ResponseApi;

public interface FindPartsByIdsUseCase {
	ResponseApi<List<PartResponseDTO>> execute(List<UUID> ids);
}
