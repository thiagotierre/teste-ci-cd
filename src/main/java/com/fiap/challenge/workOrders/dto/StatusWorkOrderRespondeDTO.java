package com.fiap.challenge.workOrders.dto;

import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;

import java.util.UUID;

public record StatusWorkOrderRespondeDTO(
        UUID id,
        WorkOrderStatus status
) {}
