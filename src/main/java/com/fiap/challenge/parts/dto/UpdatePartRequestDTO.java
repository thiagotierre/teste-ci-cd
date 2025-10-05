package com.fiap.challenge.parts.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdatePartRequestDTO(
    String name,
    String description,
    BigDecimal price,
    Integer reservedStock,
    Integer stockQuantity,
    Integer minimumStock
) {}