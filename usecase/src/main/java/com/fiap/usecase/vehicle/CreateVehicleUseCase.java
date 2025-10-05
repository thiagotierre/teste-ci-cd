package com.fiap.usecase.vehicle;

import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;

public interface CreateVehicleUseCase {
    Vehicle execute(Vehicle vehicle) throws NotFoundException, DocumentNumberException;
}