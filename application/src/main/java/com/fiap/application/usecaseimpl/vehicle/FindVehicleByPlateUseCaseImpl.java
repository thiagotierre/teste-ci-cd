package com.fiap.application.usecaseimpl.vehicle;

import com.fiap.application.gateway.vehicle.VehicleGateway;
import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.vehicle.FindVehicleByPlateUseCase;

public class FindVehicleByPlateUseCaseImpl implements FindVehicleByPlateUseCase {

    private final VehicleGateway vehicleGateway;

    public FindVehicleByPlateUseCaseImpl(VehicleGateway vehicleGateway) {
        this.vehicleGateway = vehicleGateway;
    }

    @Override
    public Vehicle execute(String plate) throws NotFoundException {
        return vehicleGateway.findByPlate(plate)
                .orElseThrow(() -> new NotFoundException("Vehicle with plate " + plate + " not found.", "VEHICLE-404"));
    }
}