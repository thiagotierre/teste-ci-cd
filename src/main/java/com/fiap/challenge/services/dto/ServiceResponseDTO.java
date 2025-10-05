package com.fiap.challenge.services.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ServiceResponseDTO(
    UUID id,
    String name,
    String description,
    BigDecimal basePrice,
    Integer estimatedTimeMin,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}