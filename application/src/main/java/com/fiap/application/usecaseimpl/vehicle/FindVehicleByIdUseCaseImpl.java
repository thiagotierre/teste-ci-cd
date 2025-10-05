package com.fiap.application.usecaseimpl.vehicle;

import com.fiap.application.gateway.vehicle.VehicleGateway;
import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.vehicle.FindVehicleByIdUseCase;
import java.util.UUID;

public class FindVehicleByIdUseCaseImpl implements FindVehicleByIdUseCase {

    private final VehicleGateway vehicleGateway;

    public FindVehicleByIdUseCaseImpl(VehicleGateway vehicleGateway) {
        this.vehicleGateway = vehicleGateway;
    }

    @Override
    public Vehicle execute(UUID id) throws NotFoundException {
        return vehicleGateway.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle with id: " + id + " not found.", "VEHICLE-404"));
    }
}