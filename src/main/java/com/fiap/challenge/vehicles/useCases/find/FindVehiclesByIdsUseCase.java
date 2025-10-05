package com.fiap.challenge.vehicles.useCases.find;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;

public interface FindVehiclesByIdsUseCase {

	ResponseApi<List<VehicleResponseDTO>> execute(List<UUID> ids);
}
