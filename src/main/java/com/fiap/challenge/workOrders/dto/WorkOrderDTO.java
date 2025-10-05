package com.fiap.challenge.workOrders.dto;

import java.util.List;
import java.util.UUID;

public record WorkOrderDTO(
        UUID customerId,
        UUID vehicleId,
        UUID createdById,
        UUID assignedMechanicId,
        List<WorkOrderPartDTO> parts,
        List<WorkOrderServiceDTO> services
) {}
