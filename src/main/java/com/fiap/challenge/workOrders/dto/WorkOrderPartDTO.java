package com.fiap.challenge.workOrders.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WorkOrderPartDTO(
        UUID partId,
        String partName,
        Integer quantity
) {}
