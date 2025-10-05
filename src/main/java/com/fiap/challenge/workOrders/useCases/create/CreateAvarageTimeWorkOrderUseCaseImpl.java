package com.fiap.challenge.workOrders.useCases.create;

import com.fiap.challenge.workOrders.entity.WorkOrderAvarageTime;
import com.fiap.challenge.workOrders.repository.AvarageTimeWorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAvarageTimeWorkOrderUseCaseImpl implements CreateAvarageTimeWorkOrderUseCase {

    private final AvarageTimeWorkOrderRepository avarageTimeWorkOrderRepository;

    @Override
    public void execute(WorkOrderAvarageTime workOrderAvarageTime) {
        avarageTimeWorkOrderRepository.save(workOrderAvarageTime);
    }
}
