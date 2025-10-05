package com.fiap.core.domain.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {
	private UUID id;
	private String name;
	private String description;
	private BigDecimal basePrice;
	private Integer estimatedTimeMin;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
}