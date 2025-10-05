package com.fiap.challenge.parts.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PartResponseDTO(
	UUID id,
    String name,
    String description,
    BigDecimal price,
    Integer stockQuantity,
    Integer reservedStock,
    Integer minimumStock,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}
