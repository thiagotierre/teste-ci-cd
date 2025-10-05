package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;

import java.util.UUID;

public interface UpdateStatusWorkOrderUseCase {
    ResponseApi<StatusWorkOrderRespondeDTO> execute(UUID workOrderId, String status);
}
