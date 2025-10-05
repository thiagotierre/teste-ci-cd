package com.fiap.dto.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateServiceRequest(
        @NotBlank String name,
        String description,
        @NotNull @Min(0) BigDecimal basePrice,
        @Min(0) Integer estimatedTimeMin
) {}