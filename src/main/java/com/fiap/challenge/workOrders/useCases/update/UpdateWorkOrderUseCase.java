package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;

public interface UpdateWorkOrderUseCase {

    public void execute(WorkOrderModel workOrderModel);
}
