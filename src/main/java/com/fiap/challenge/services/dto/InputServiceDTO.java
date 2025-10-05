package com.fiap.challenge.services.dto;

import java.math.BigDecimal;

public record InputServiceDTO(
    String name,
    String description,
    BigDecimal basePrice,
    Integer estimatedTimeMin
) {}