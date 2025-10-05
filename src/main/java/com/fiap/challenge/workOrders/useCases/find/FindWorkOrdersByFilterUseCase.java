package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.WorkOrderFilterDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResumeDTO;

import java.util.List;

public interface FindWorkOrdersByFilterUseCase {

    ResponseApi<List<WorkOrderResumeDTO>> execute(WorkOrderFilterDTO filterDTO);
}
