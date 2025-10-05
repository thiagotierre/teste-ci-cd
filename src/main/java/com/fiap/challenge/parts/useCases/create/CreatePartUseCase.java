package com.fiap.challenge.parts.useCases.create;

import com.fiap.challenge.parts.dto.CreatePartRequestDTO;
import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.shared.model.ResponseApi;

public interface CreatePartUseCase {

	ResponseApi<PartResponseDTO> execute(CreatePartRequestDTO partRequest);
}
 