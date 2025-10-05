package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.WorkOrderItemDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResumeDTO;

import java.util.UUID;

public interface UpdateWorkOrderItemsUseCase {

    ResponseApi<WorkOrderResumeDTO> execute(UUID id, WorkOrderItemDTO workOrderItemDTO);
}
