package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.entity.WorkOrderAvarageTime;

import java.util.List;
import java.util.UUID;

public interface FindAvarageTimeWorkOrderUseCase {
    WorkOrderAvarageTime execute(UUID id);
    ResponseApi<List<WorkOrderAvarageTime>> executeList();
}
