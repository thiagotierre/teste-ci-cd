package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateWorkOrderUseCaseImpl implements UpdateWorkOrderUseCase {

    private final WorkOrderRepository workOrderRepository;

    public UpdateWorkOrderUseCaseImpl(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    @Override
    public void execute(WorkOrderModel workOrderModel) {
        workOrderRepository.save(workOrderModel);
    }
}
