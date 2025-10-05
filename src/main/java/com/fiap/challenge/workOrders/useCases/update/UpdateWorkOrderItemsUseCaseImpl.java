package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.parts.useCases.update.SubtractPartsFromStockUseCase;
import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.WorkOrderItemDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResumeDTO;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.mapper.WorkOrderMapper;
import com.fiap.challenge.workOrders.useCases.create.CreateWorkOrderPartUseCase;
import com.fiap.challenge.workOrders.useCases.create.CreateWorkOrderServiceUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrderByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateWorkOrderItemsUseCaseImpl implements  UpdateWorkOrderItemsUseCase{

    private final FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;
    private final UpdateWorkOrderUseCase updateWorkOrderUseCase;
    private final CreateWorkOrderPartUseCase createWorkOrderPartUseCase;
    private final CreateWorkOrderServiceUseCase createWorkOrderServiceUseCase;
    private final UpdateStatusWorkOrderUseCase updateStatusWorkOrderUseCase;
    private final WorkOrderMapper workOrderMapper;
    private final SubtractPartsFromStockUseCase subtractPartsFromStockUseCase;

    @Override
    @Transactional
    public ResponseApi<WorkOrderResumeDTO> execute(UUID id, WorkOrderItemDTO workOrderItemDTO) {
        ResponseApi<WorkOrderResumeDTO> responseApi = new ResponseApi<>();

        var workOrder = findWorkOrderByIdUseCase.execute(id);

        createWorkOrderPartUseCase.execute(workOrder, workOrderItemDTO.parts());
        createWorkOrderServiceUseCase.execute(workOrder,workOrderItemDTO.services());

        workOrderItemDTO.parts().forEach(part -> {
            if(!subtractPartsFromStockUseCase.execute(part.partId(), part.quantity()))
                throw new IllegalArgumentException("Estoque insuficiente para a peça com ID: " + part.partId());
        });

        workOrder.recalculateTotal(); // Agora a soma é responsabilidade da entidade
        updateStatusWorkOrderUseCase.execute(workOrder.getId(), WorkOrderStatus.AWAITING_APPROVAL.name());

        updateWorkOrderUseCase.execute(workOrder);

        return responseApi.of(HttpStatus.OK, "Ordem de serviço atualizada com sucesso!", workOrderMapper.toWorkOrderResumeDTO(workOrder));
    }
}
