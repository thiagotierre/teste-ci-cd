package com.fiap.challenge.vehicles.useCases.find;

import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;

public interface FindVehicleByIdUseCase {
	ResponseApi<VehicleResponseDTO> execute(UUID id);
}
