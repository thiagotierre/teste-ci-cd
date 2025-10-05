package com.fiap.challenge.workOrders.useCases.create;

import com.fiap.challenge.parts.useCases.update.SubtractPartsFromStockUseCase;
import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.WorkOrderResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.users.repository.UserRepository;
import com.fiap.challenge.workOrders.dto.WorkOrderDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderHistoryCommand;
import com.fiap.challenge.workOrders.history.useCases.updateStatus.UpdateWorkOrderHistoryUseCase;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CreateWorkOrderUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final CreateWorkOrderPartUseCase createWorkOrderPartUseCase;
    private final CreateWorkOrderServiceUseCase createWorkOrderServiceUseCase;
    private final UpdateWorkOrderHistoryUseCase updateWorkOrderStatusUseCase;
    private final SubtractPartsFromStockUseCase subtractPartsFromStockUseCase;

    @Transactional
    public ResponseApi<WorkOrderResponseDTO> execute(WorkOrderDTO dto) {
        ResponseApi<WorkOrderResponseDTO> responseApi = new ResponseApi<>();

        var customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        var vehicle = vehicleRepository.findById(dto.vehicleId())
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));

        var createdBy = userRepository.findById(dto.createdById())
                .orElseThrow(() -> new EntityNotFoundException("Usuário criador não encontrado"));

        var mechanic = dto.assignedMechanicId() != null
                ? userRepository.findById(dto.assignedMechanicId())
                .orElseThrow(() -> new EntityNotFoundException("Mecânico atribuído não encontrado"))
                : null;

        // Cria a OS com status inicial correto
        WorkOrderModel workOrder = WorkOrderModel.builder()
                .customer(customer)
                .vehicle(vehicle)
                .createdBy(createdBy)
                .assignedMechanic(mechanic)
                .workOrderServices(new ArrayList<>())
                .workOrderPartModels(new ArrayList<>())
                .status(WorkOrderStatus.RECEIVED) // Corrigido: status válido
                .build();

        createWorkOrderPartUseCase.execute(workOrder, dto.parts());
        dto.parts().forEach(part -> {
            if(!subtractPartsFromStockUseCase.execute(part.partId(), part.quantity()))
                throw new IllegalArgumentException("Estoque insuficiente para a peça com ID: " + part.partId());
        });
        createWorkOrderServiceUseCase.execute(workOrder, dto.services());

        if (workOrder.getWorkOrderServices().isEmpty() && workOrder.getWorkOrderPartModels().isEmpty())
            throw new IllegalArgumentException("Cada item deve ter ao menos uma peça ou serviço associado");

        workOrder.recalculateTotal(); // Agora a soma é responsabilidade da entidade

        workOrder = workOrderRepository.save(workOrder);

        updateWorkOrderStatusUseCase.execute(new UpdateWorkOrderHistoryCommand(workOrder.getId()));

        WorkOrderResponseDTO workOrderResponseDTO = new WorkOrderResponseDTO(
                workOrder.getId(),
                workOrder.getCustomer().getId(),
                workOrder.getVehicle().getId(),
                workOrder.getCreatedBy().getId(),
                workOrder.getAssignedMechanic() != null ? workOrder.getAssignedMechanic().getId() : null,
                workOrder.getTotalAmount(),
                dto.parts(),
                dto.services()
        );
        return responseApi.of(HttpStatus.CREATED, "Ordem de serviço criada com sucesso!", workOrderResponseDTO);
    }
}
