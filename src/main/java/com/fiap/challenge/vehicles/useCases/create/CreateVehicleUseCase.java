package com.fiap.challenge.vehicles.useCases.create;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.vehicles.dto.InputVehicleDTO;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;

public interface CreateVehicleUseCase {
	ResponseApi<VehicleResponseDTO> execute(InputVehicleDTO dto);
}
