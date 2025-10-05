package com.fiap.challenge.workOrders.history.useCases.updateStatus;

import org.springframework.stereotype.Service;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.history.WorkOrderHistoryModel;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderHistoryCommand;
import com.fiap.challenge.workOrders.history.repository.WorkOrderHistoryRepository;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateWorkOrderHistoryUseCaseImpl implements UpdateWorkOrderHistoryUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderHistoryRepository workOrderHistoryRepository;

    @Override
    public WorkOrderModel execute(UpdateWorkOrderHistoryCommand command) {
        WorkOrderModel workOrder = workOrderRepository.findById(command.workOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Work Order not found: " + command.workOrderId()));

        WorkOrderHistoryModel historyEntry = WorkOrderHistoryModel.builder()
                .workOrder(workOrder)
                .status(workOrder.getStatus())
                .notes(command.notes())
                .build();

        workOrderHistoryRepository.save(historyEntry);

        return workOrder;
    }
}