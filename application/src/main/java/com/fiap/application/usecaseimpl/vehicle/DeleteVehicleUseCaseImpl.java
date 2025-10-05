package com.fiap.application.usecaseimpl.vehicle;

import com.fiap.application.gateway.vehicle.VehicleGateway;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.vehicle.DeleteVehicleUseCase;
import java.util.UUID;

public class DeleteVehicleUseCaseImpl implements DeleteVehicleUseCase {

    private final VehicleGateway vehicleGateway;

    public DeleteVehicleUseCaseImpl(VehicleGateway vehicleGateway) {
        this.vehicleGateway = vehicleGateway;
    }

    @Override
    public void execute(UUID id) throws NotFoundException {
        if (!vehicleGateway.existsById(id)) {
            throw new NotFoundException("Vehicle with id: " + id + " not found.", "VEHICLE-404");
        }
        vehicleGateway.delete(id);
    }
}