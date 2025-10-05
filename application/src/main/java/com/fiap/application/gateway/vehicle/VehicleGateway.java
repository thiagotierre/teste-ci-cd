package com.fiap.application.gateway.vehicle;

import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.core.exception.DocumentNumberException;

import java.util.Optional;
import java.util.UUID;

public interface VehicleGateway {
    Vehicle create(Vehicle vehicle) throws DocumentNumberException;
    Vehicle update(Vehicle vehicle) throws DocumentNumberException;
    void delete(UUID id);
    boolean existsById(UUID id);
    Optional<Vehicle> findById(UUID id);
    Optional<Vehicle> findByPlate(String plate);
}