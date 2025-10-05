package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.WorkOrderResponseDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;

import java.util.UUID;

public interface FindWorkOrderByIdUseCase {

    public WorkOrderModel execute(UUID id);

    ResponseApi<WorkOrderResponseDTO> executeToDTO(UUID id);
}
