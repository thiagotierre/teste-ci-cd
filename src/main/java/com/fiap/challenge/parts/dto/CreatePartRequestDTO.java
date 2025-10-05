package com.fiap.challenge.parts.dto;

import java.math.BigDecimal;

public record CreatePartRequestDTO (
	String name,
	String description,
	BigDecimal price,
    Integer stockQuantity,
    Integer minimumStock
) {}
