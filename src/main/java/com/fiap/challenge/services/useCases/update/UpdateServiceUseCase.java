package com.fiap.challenge.services.useCases.update;

import java.util.UUID;

import com.fiap.challenge.services.dto.InputServiceDTO;
import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.shared.model.ResponseApi;

public interface UpdateServiceUseCase {

	ResponseApi<ServiceResponseDTO> execute(UUID id, InputServiceDTO updateServiceDTO);
}
