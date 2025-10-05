package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.entity.WorkOrderAvarageTime;
import com.fiap.challenge.workOrders.useCases.create.CreateAvarageTimeWorkOrderUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindAvarageTimeWorkOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvarageTimeWorkOrderUseCaseImpl implements AvarageTimeWorkOrderUseCase {

    private final CreateAvarageTimeWorkOrderUseCase createAvarageTimeWorkOrderUseCase;
    private final FindAvarageTimeWorkOrderUseCase findAvarageTimeWorkOrderUseCase;

    @Override
    public void executeInit(UUID id) {
        WorkOrderAvarageTime workOrderAvarageTime = new WorkOrderAvarageTime();
        workOrderAvarageTime.setWorkOrderId(id);
        workOrderAvarageTime.initTime();

        createAvarageTimeWorkOrderUseCase.execute(workOrderAvarageTime);
    }

    @Override
    public void executeEnd(UUID id) {
        WorkOrderAvarageTime workOrderAvarageTime = findAvarageTimeWorkOrderUseCase.execute(id);
        if (Objects.isNull(workOrderAvarageTime)) throw new IllegalArgumentException("Avarage time work order not found for ID: " + id);

        workOrderAvarageTime.finishTime();
        createAvarageTimeWorkOrderUseCase.execute(workOrderAvarageTime);
    }


}
