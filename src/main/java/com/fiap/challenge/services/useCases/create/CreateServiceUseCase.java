package com.fiap.challenge.services.useCases.create;

import com.fiap.challenge.services.dto.InputServiceDTO;
import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.shared.model.ResponseApi;

public interface CreateServiceUseCase {

	ResponseApi<ServiceResponseDTO> execute(InputServiceDTO createServiceDTO);
}
