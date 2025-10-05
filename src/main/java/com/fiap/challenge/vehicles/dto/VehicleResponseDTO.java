package com.fiap.challenge.vehicles.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fiap.challenge.customers.dto.CustomerInfo;

public record VehicleResponseDTO(
    UUID id,
    CustomerInfo customer,
    String licensePlate,
    String brand,
    String model,
    Integer year,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}