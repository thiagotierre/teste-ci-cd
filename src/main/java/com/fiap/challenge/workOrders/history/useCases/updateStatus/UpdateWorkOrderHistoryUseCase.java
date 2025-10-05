package com.fiap.challenge.workOrders.history.useCases.updateStatus;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderHistoryCommand;

public interface UpdateWorkOrderHistoryUseCase {
    public WorkOrderModel execute(UpdateWorkOrderHistoryCommand command);
}
