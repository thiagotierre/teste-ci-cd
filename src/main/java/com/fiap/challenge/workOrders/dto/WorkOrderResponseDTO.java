package com.fiap.challenge.workOrders.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record WorkOrderResponseDTO(
        UUID id,
        UUID customerId,
        UUID vehicleId,
        UUID createdById,
        UUID assignedMechanicId,
        BigDecimal totalAmount,
        List<WorkOrderPartDTO> parts,
        List<WorkOrderServiceDTO> services
) {}
