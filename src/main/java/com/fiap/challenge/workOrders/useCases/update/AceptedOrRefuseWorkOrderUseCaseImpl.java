package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.parts.useCases.update.ReturnPartsToStockUseCase;
import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderHistoryCommand;
import com.fiap.challenge.workOrders.history.useCases.updateStatus.UpdateWorkOrderHistoryUseCase;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AceptedOrRefuseWorkOrderUseCaseImpl implements AceptedOrRefuseWorkOrderUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final UpdateWorkOrderHistoryUseCase updateWorkOrderStatusUseCase;
    private final ReturnPartsToStockUseCase returnPartsToStockUseCase;
    private final AvarageTimeWorkOrderUseCase avarageTimeWorkOrderUseCase;

    @Transactional
    @Override
    public ResponseApi<StatusWorkOrderRespondeDTO> execute(UUID workOrderId, boolean accepted) {
        ResponseApi<StatusWorkOrderRespondeDTO> responseApi = new ResponseApi<>();
        var workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Work order not found with id: " + workOrderId));

        if (!accepted) {
            workOrder.setStatus(WorkOrderStatus.REFUSED);
            workOrder.getWorkOrderPartModels().forEach(workOrderPart -> {
                var partId = workOrderPart.getPart().getId();
                var quantity = workOrderPart.getQuantity();
                returnPartsToStockUseCase.execute(partId, quantity);
            });
        }else {
            workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
        }
        WorkOrderModel workOrderModel= workOrderRepository.save(workOrder);
        avarageTimeWorkOrderUseCase.executeInit(workOrderModel.getId());

        updateWorkOrderStatusUseCase.execute(new UpdateWorkOrderHistoryCommand(workOrder.getId()));

        return responseApi.of(HttpStatus.OK, "Work order status updated successfully!",
                new StatusWorkOrderRespondeDTO(
            workOrderModel.getId(),
            workOrderModel.getStatus()));
    }
}
