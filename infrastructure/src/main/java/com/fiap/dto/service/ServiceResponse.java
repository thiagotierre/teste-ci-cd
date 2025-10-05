package com.fiap.dto.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ServiceResponse(
        UUID id,
        String name,
        String description,
        BigDecimal basePrice,
        Integer estimatedTimeMin,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}