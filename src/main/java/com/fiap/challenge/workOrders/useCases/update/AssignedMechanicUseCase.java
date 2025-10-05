package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.AssignedMechanicResponseDTO;
import com.fiap.challenge.workOrders.dto.InputAssignMechanicDTO;

import java.util.UUID;

public interface AssignedMechanicUseCase {

    ResponseApi<AssignedMechanicResponseDTO> execute(UUID workOrderId, InputAssignMechanicDTO inputAssignMechanicDTO);
}
