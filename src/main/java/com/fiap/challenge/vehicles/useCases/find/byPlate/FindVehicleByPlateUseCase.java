package com.fiap.challenge.vehicles.useCases.find.byPlate;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;

public interface FindVehicleByPlateUseCase {

	ResponseApi<VehicleResponseDTO> execute(String plate);
}
