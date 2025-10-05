package com.fiap.challenge.workOrders.dto;

import java.util.List;

public record WorkOrderItemDTO(
        List<WorkOrderPartDTO> parts,
        List<WorkOrderServiceDTO> services
) {}
