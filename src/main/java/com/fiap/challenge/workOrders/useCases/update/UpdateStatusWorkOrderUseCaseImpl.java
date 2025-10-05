package com.fiap.challenge.workOrders.useCases.update;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderHistoryCommand;
import com.fiap.challenge.workOrders.history.useCases.updateStatus.UpdateWorkOrderHistoryUseCase;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateStatusWorkOrderUseCaseImpl implements UpdateStatusWorkOrderUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final UpdateWorkOrderHistoryUseCase updateWorkOrderHistoryUseCase;

    @Override
    public ResponseApi<StatusWorkOrderRespondeDTO> execute(UUID id, String status) {
        var workOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Work order not found with id: " + id));

        WorkOrderStatus newStatus;
        try {
            newStatus = WorkOrderStatus.fromString(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        if (workOrder.getStatus() == newStatus) {
            return new ResponseApi<StatusWorkOrderRespondeDTO>().of(HttpStatus.OK,
                    "Work order status is already '" + status + "'. No changes were made.",
                    new StatusWorkOrderRespondeDTO(workOrder.getId(), workOrder.getStatus()));
        }

        workOrder.setStatus(newStatus);
        WorkOrderModel updatedWorkOrder = workOrderRepository.save(workOrder);

        updateWorkOrderHistoryUseCase.execute(new UpdateWorkOrderHistoryCommand(updatedWorkOrder.getId()));

        return new ResponseApi<StatusWorkOrderRespondeDTO>().of(HttpStatus.OK, "Work order status updated successfully!",
                new StatusWorkOrderRespondeDTO(updatedWorkOrder.getId(), updatedWorkOrder.getStatus()));
    }
}