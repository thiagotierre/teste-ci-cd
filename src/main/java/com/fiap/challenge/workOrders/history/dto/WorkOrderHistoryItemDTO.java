package com.fiap.challenge.workOrders.history.dto;

import java.time.OffsetDateTime;

public record WorkOrderHistoryItemDTO(
	String status, 
	String notes,
	OffsetDateTime createdAt
) {}