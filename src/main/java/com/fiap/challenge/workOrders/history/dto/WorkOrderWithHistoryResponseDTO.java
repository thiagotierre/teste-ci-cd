package com.fiap.challenge.workOrders.history.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record WorkOrderWithHistoryResponseDTO(
    UUID workOrderId,
    BigDecimal totalAmount,
    List<WorkOrderHistoryItemDTO> history
) {}