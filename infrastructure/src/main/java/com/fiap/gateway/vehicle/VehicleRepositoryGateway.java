package com.fiap.gateway.vehicle;

import com.fiap.application.gateway.vehicle.VehicleGateway;
import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.mapper.vehicle.VehicleMapper;
import com.fiap.persistence.entity.vehicle.VehicleEntity;
import com.fiap.persistence.repository.vehicle.VehicleEntityRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class VehicleRepositoryGateway implements VehicleGateway {

    private final VehicleEntityRepository repository;
    private final VehicleMapper mapper;

    public VehicleRepositoryGateway(VehicleEntityRepository repository, VehicleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        VehicleEntity entity = mapper.toEntity(vehicle);
        VehicleEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        VehicleEntity entity = mapper.toEntity(vehicle);
        VehicleEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<Vehicle> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Vehicle> findByPlate(String plate) {
        return repository.findByLicensePlateIgnoreCase(plate).map(mapper::toDomain);
    }
}