package com.fiap.challenge.workOrders.history.dto;

import java.util.UUID;

import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;

import jakarta.annotation.Nullable;

public record UpdateWorkOrderHistoryCommand(
	UUID workOrderId,
	@Nullable String notes
) {

    public UpdateWorkOrderHistoryCommand(UUID workOrderId) {
        this(workOrderId, null);
    }
}