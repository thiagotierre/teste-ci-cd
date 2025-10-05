package com.fiap.usecase.vehicle;

import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.core.exception.NotFoundException;
import java.util.UUID;

public interface FindVehicleByIdUseCase {
    Vehicle execute(UUID id) throws NotFoundException;
}