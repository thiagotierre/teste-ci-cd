package com.fiap.challenge.workOrders.useCases.update;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.shared.exception.workOrder.WorkOrderNotAvailableException;
import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.usecases.find.FindUserByIdUseCase;
import com.fiap.challenge.workOrders.dto.AssignedMechanicResponseDTO;
import com.fiap.challenge.workOrders.dto.InputAssignMechanicDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderHistoryCommand;
import com.fiap.challenge.workOrders.history.useCases.updateStatus.UpdateWorkOrderHistoryUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrderByIdUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignedMechanicUseCaseImpl implements AssignedMechanicUseCase{

    private final FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;
    private final UpdateWorkOrderUseCase updateWorkOrderUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final UpdateWorkOrderHistoryUseCase updateWorkOrderHistoryUseCase;

    @Override
    public ResponseApi<AssignedMechanicResponseDTO> execute(UUID workOrderId, InputAssignMechanicDTO inputAssignMechanicDTO) {
        ResponseApi<AssignedMechanicResponseDTO> responseApi = new ResponseApi<>();
        WorkOrderModel workOrderModel = findWorkOrderByIdUseCase.execute(workOrderId);

        if (workOrderModel.getStatus() != WorkOrderStatus.RECEIVED) {
            throw new WorkOrderNotAvailableException((workOrderId));
        }

        UserModel userModel = findUserByIdUseCase.execute(inputAssignMechanicDTO.mechanicId());

        workOrderModel.setAssignedMechanic(userModel);
        workOrderModel.setStatus(WorkOrderStatus.IN_DIAGNOSIS);
        updateWorkOrderUseCase.execute(workOrderModel);

        updateWorkOrderHistoryUseCase.execute(new UpdateWorkOrderHistoryCommand(workOrderId));

        return responseApi.of(HttpStatus.OK, "Mecânico atribuído com sucesso!",
                new AssignedMechanicResponseDTO(true));
    }
}
