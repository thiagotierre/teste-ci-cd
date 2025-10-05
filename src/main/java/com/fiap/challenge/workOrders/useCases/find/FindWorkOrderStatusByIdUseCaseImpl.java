package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindWorkOrderStatusByIdUseCaseImpl implements FindWorkOrderStatusByIdUseCase {
    private final FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;

    @Override
    public Optional<String> execute(UUID id) {
        WorkOrderModel workOrderModel = findWorkOrderByIdUseCase.execute(id);

        if(Objects.isNull(workOrderModel)){
            throw new EntityNotFoundException("Work order not found");
        }

        return Optional.ofNullable(workOrderModel.getStatus())
                       .map(Enum::name);
    }
}
