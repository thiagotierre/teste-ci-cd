package com.fiap.challenge.services.useCases.find;

import java.util.UUID;

import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.shared.model.ResponseApi;

public interface FindServiceByIdUseCase {

	ResponseApi<ServiceResponseDTO> execute(UUID id);
}
