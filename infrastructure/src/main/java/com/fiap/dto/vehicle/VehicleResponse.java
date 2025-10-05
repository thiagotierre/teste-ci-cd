package com.fiap.dto.vehicle;

import com.fiap.dto.customer.CustomerResponse;
import java.time.OffsetDateTime;
import java.util.UUID;

public record VehicleResponse(
        UUID id,
        CustomerResponse customer,
        String licensePlate,
        String brand,
        String model,
        Integer year,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}