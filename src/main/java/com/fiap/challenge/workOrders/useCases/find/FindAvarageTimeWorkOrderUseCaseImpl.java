package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.entity.WorkOrderAvarageTime;
import com.fiap.challenge.workOrders.repository.AvarageTimeWorkOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindAvarageTimeWorkOrderUseCaseImpl implements FindAvarageTimeWorkOrderUseCase {

    private final AvarageTimeWorkOrderRepository avarageTimeWorkOrderRepository;
    @Override
    public WorkOrderAvarageTime execute(UUID id) {
        WorkOrderAvarageTime workOrderAvarageTime = avarageTimeWorkOrderRepository.findByWorkOrderId(id).orElse(null);
        if (Objects.isNull(workOrderAvarageTime)) throw new EntityNotFoundException("Entity not found with id: " + id);
        return workOrderAvarageTime;
    }

    @Override
    public ResponseApi<List<WorkOrderAvarageTime>> executeList() {
        ResponseApi<List<WorkOrderAvarageTime>> responseApi = new ResponseApi<>();
        List<WorkOrderAvarageTime> workOrderAvarageTimes = avarageTimeWorkOrderRepository.findAll();
        if (workOrderAvarageTimes.isEmpty()) return responseApi.of(HttpStatus.NOT_FOUND,"No average time work orders found.");

        return responseApi.of(HttpStatus.OK, "Average time work orders retrieved successfully.", workOrderAvarageTimes);
    }
}
