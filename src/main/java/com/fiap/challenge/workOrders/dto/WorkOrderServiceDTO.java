package com.fiap.challenge.workOrders.dto;

import java.util.UUID;

public record WorkOrderServiceDTO(
        UUID serviceId,
        String serviceName,
        Integer quantity
) {}
