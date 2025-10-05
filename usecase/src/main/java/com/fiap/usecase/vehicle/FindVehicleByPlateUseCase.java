package com.fiap.usecase.vehicle;

import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.core.exception.NotFoundException;

public interface FindVehicleByPlateUseCase {
    Vehicle execute(String plate) throws NotFoundException;
}