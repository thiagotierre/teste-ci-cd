package com.fiap.challenge.workOrders.useCases.create;

import com.fiap.challenge.workOrders.dto.WorkOrderPartDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;

import java.util.List;

public interface CreateWorkOrderPartUseCase {

    public void execute(WorkOrderModel workOrderModel, List<WorkOrderPartDTO> workOrderPartDTOS);
}
