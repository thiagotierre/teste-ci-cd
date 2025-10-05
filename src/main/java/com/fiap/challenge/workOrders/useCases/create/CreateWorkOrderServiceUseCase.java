package com.fiap.challenge.workOrders.useCases.create;

import com.fiap.challenge.workOrders.dto.WorkOrderServiceDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;

import java.util.List;

public interface CreateWorkOrderServiceUseCase {

    public void execute(WorkOrderModel workOrderModel, List<WorkOrderServiceDTO> workOrderServiceDTOS);
}
