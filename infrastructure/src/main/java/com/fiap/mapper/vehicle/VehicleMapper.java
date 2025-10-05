package com.fiap.mapper.vehicle;

import com.fiap.core.domain.customer.Customer;
import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.dto.vehicle.CreateVehicleRequest;
import com.fiap.dto.vehicle.UpdateVehicleRequest;
import com.fiap.dto.vehicle.VehicleResponse;
import com.fiap.mapper.customer.CustomerMapper;
import com.fiap.persistence.entity.customer.CustomerEntity;
import com.fiap.persistence.entity.vehicle.VehicleEntity;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class VehicleMapper {

    private final CustomerMapper customerMapper;

    public VehicleMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public Vehicle toDomain(CreateVehicleRequest request) {
        return Vehicle.builder()
                .customer(customerMapper.toDomain(request.customerId()))
                .licensePlate(request.licensePlate())
                .brand(request.brand())
                .model(request.model())
                .year(request.year())
                .build();
    }

    public Vehicle toDomain(UUID id, UpdateVehicleRequest request) {
        return Vehicle.builder()
                .id(id)
                .customer(customerMapper.toDomain(request.customerId()))
                .licensePlate(request.licensePlate())
                .brand(request.brand())
                .model(request.model())
                .year(request.year())
                .build();
    }

    public VehicleResponse toResponse(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                customerMapper.toResponse(vehicle.getCustomer()),
                vehicle.getLicensePlate(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }

    public VehicleEntity toEntity(Vehicle vehicle) {
        return VehicleEntity.builder()
                .id(vehicle.getId())
                .customer(CustomerEntity.builder().id(vehicle.getCustomer().getId()).build())
                .licensePlate(vehicle.getLicensePlate())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }


    public Vehicle toDomain(VehicleEntity entity) {
        return Vehicle.builder()
                .id(entity.getId())
                .customer(customerMapper.toDomain(entity.getCustomer()))
                .licensePlate(entity.getLicensePlate())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .year(entity.getYear())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}