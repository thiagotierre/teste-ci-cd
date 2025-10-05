package com.fiap.challenge.vehicles.useCases.delete;

import com.fiap.challenge.shared.model.ResponseApi;

import java.util.UUID;

public interface DeleteVehicleUseCase {

	ResponseApi<Void> execute(UUID id);
}
