package com.fiap.dto.part;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record UpdatePartRequest(
        @NotBlank String name,
        String description,
        @NotNull @Min(0) BigDecimal price,
        @NotNull @Min(0) Integer stockQuantity,
        @NotNull @Min(0) Integer reservedStock,
        @NotNull @Min(0) Integer minimumStock
) {}