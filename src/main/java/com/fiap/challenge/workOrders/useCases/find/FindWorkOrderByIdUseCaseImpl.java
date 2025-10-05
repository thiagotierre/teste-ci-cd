package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.shared.exception.workOrder.WorkOrderNotFoundException;
import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.WorkOrderResponseDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.mapper.WorkOrderPartMapper;
import com.fiap.challenge.workOrders.mapper.WorkOrderServiceMapper;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindWorkOrderByIdUseCaseImpl implements FindWorkOrderByIdUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderPartMapper workOrderPartMapper;
    private final WorkOrderServiceMapper workOrderServiceMapper;

    @Override
    public WorkOrderModel execute(UUID workOrderId) {
        return workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new WorkOrderNotFoundException(workOrderId));
    }

    @Override
    @Transactional
    public ResponseApi<WorkOrderResponseDTO> executeToDTO(UUID id) {
        ResponseApi<WorkOrderResponseDTO> responseApi = new ResponseApi<>();
        var workOrderModel = this.execute(id);
        var workOrderPartsDTO = workOrderModel.getWorkOrderPartModels().stream()
                .map(workOrderPartMapper::toWorkOrderPartDTO).toList();

        var workOrderServicesDTO = workOrderModel.getWorkOrderServices().stream()
                .map(workOrderServiceMapper::toWorkOrderServiceDTO).toList();

        return responseApi.of(HttpStatus.OK, "Ordem de serviço encontrada com sucesso!",
                new WorkOrderResponseDTO(
                workOrderModel.getId(),
                workOrderModel.getCustomer().getId(),
                workOrderModel.getVehicle().getId(),
                workOrderModel.getCreatedBy().getId(),
                workOrderModel.getAssignedMechanic() != null ? workOrderModel.getAssignedMechanic().getId() : null,
                workOrderModel.getTotalAmount(),
                workOrderPartsDTO,
                workOrderServicesDTO
        ));
    }


}
