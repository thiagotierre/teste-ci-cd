package com.fiap.usecase.vehicle;

import com.fiap.core.exception.NotFoundException;
import java.util.UUID;

public interface DeleteVehicleUseCase {
    void execute(UUID id) throws NotFoundException;
}