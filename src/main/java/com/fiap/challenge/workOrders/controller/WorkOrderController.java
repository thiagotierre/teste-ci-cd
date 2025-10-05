package com.fiap.challenge.workOrders.controller;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.*;

import com.fiap.challenge.workOrders.entity.WorkOrderAvarageTime;
import com.fiap.challenge.workOrders.useCases.find.FindAvarageTimeWorkOrderUseCase;
import com.fiap.challenge.workOrders.useCases.update.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.workOrders.dto.AssignedMechanicResponseDTO;
import com.fiap.challenge.workOrders.dto.InputAssignMechanicDTO;
import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderItemDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResponseDTO;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.useCases.create.CreateWorkOrderUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrderByIdUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrdersByFilterUseCase;
import com.fiap.challenge.workOrders.history.dto.WorkOrderWithHistoryResponseDTO;
import com.fiap.challenge.workOrders.history.useCases.get.GetWorkOrderHistoryByCpfUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/work-orders")
@RequiredArgsConstructor
@Tag(name = "Ordem de Serviço", description = "Controlador para ordem de serviço")
public class WorkOrderController {

    private final UpdateStatusWorkOrderUseCase updateStatusWorkOrderUseCase;
    private final AceptedOrRefuseWorkOrderUseCase aceptedOrRefuseWorkOrderUseCase;
    private final AssignedMechanicUseCase assignedMechanicUseCase;
    private final CreateWorkOrderUseCase createWorkOrderUseCase;
    private final FindWorkOrdersByFilterUseCase findWorkOrdersByFilterUseCase;
    private final FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;
    private final UpdateWorkOrderItemsUseCase updateWorkOrderItemsUseCase;
    private final GetWorkOrderHistoryByCpfUseCase getWorkOrderHistoryByCpfUseCase;
    private final FinalizeWorkOrderUseCase finalizeWorkOrderUseCase;
    private final FindAvarageTimeWorkOrderUseCase findAvarageTimeWorkOrderUseCase;

    @Operation(
        summary = "Altera o status de uma ordem de serviço",
        description = "Endpoint para alterar o status de uma ordem de serviço pelo ID")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Status alterado com sucesso.") })
    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseApi<StatusWorkOrderRespondeDTO>> updateStatus(@PathVariable UUID id, @RequestBody String status) {
        ResponseApi<StatusWorkOrderRespondeDTO> responseApi = updateStatusWorkOrderUseCase.execute(id, status);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
        summary = "Aceita ou recusa uma ordem de serviço",
        description = "Endpoint para aceitar ou recusar uma ordem de serviço pelo ID")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço aceita ou recusada com sucesso.") })
    @PatchMapping("/{id}/decision")
    public ResponseEntity<ResponseApi<StatusWorkOrderRespondeDTO>> aceptedOrRefuse(@PathVariable UUID id, @RequestBody boolean decision) {
        ResponseApi<StatusWorkOrderRespondeDTO> responseApi = aceptedOrRefuseWorkOrderUseCase.execute(id, decision);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @PatchMapping("/{id}/delivered")
    @Operation(
            summary = "Marca uma ordem de serviço como entregue",
            description = "Endpoint para marcar uma ordem de serviço como entregue pelo ID")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço marcada como entregue com sucesso.") })
    public ResponseEntity<ResponseApi<StatusWorkOrderRespondeDTO>> markAsDelivered(@PathVariable UUID id) {
        ResponseApi<StatusWorkOrderRespondeDTO> responseApi = updateStatusWorkOrderUseCase.execute(id, "DELIVERED");
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }


    @Operation(
            summary = "Finaliza uma ordem de serviço",
            description = "Endpoint para finalizar uma ordem de serviço pelo ID")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço finalizada com sucesso.") })
    @PatchMapping("/{id}/finalize")
    public ResponseEntity<ResponseApi<Void>> finalizeWorkOrder(@PathVariable UUID id) {
        ResponseApi<Void> responseApi = finalizeWorkOrderUseCase.execute(id);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
            summary = "Vincula um mecânico a uma ordem de serviço",
            description = "Endpoint para vincular um mecânico a uma ordem de serviço pelo ID da OS e do Mecânico")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Mecânico vinculado com sucesso.") })
    @PatchMapping("/{id}/assign-mechanic")
    public ResponseEntity<ResponseApi<AssignedMechanicResponseDTO>> assignMechanic(@PathVariable UUID id, @RequestBody InputAssignMechanicDTO inputAssignMechanicDTO) {
        ResponseApi<AssignedMechanicResponseDTO> responseApi = assignedMechanicUseCase.execute(id, inputAssignMechanicDTO);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
            summary = "Cria uma ordem de serviço",
            description = "Endpoint para criar uma ordem de serviço")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "201", description = "Ordem de serviço criada com sucesso.") })
  	@PostMapping
    public ResponseEntity<ResponseApi<WorkOrderResponseDTO>> createWorkOrder(@RequestBody WorkOrderDTO dto) {
        ResponseApi<WorkOrderResponseDTO> responseApi = createWorkOrderUseCase.execute(dto);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
            summary = "Retorna uma ordem de serviço pelo ID",
            description = "Endpoint para retornar uma ordem de serviço pelo ID")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço encontrada com sucesso.") })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<WorkOrderResponseDTO>> getWorkOrderById(@PathVariable UUID id) {
        ResponseApi<WorkOrderResponseDTO> responseApi = findWorkOrderByIdUseCase.executeToDTO(id);

        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
            summary = "Retorna Lista de Ordem de Serviço",
            description = "Endpoint para retornar OS, podendo filtrar pelo status")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordens de Serviço retornadas com sucesso.") })
    @GetMapping("/list")
    public ResponseEntity<ResponseApi<List<WorkOrderResumeDTO>>> getWorkOrders(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        WorkOrderFilterDTO filter = new WorkOrderFilterDTO();

        if (status != null) filter.setStatus(WorkOrderStatus.fromString(status));

        ResponseApi<List<WorkOrderResumeDTO>> responseApi = findWorkOrdersByFilterUseCase.execute(filter);

        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
            summary = "Adiciona novos itens para a Ordem de Serviço",
            description = "Endpoint para adicionar novas peças/insumos para a ordem de serviço")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Itens adicionados com sucesso.") })
    @PatchMapping("/{id}/update-items")
    public ResponseEntity<ResponseApi<WorkOrderResumeDTO>> updateItems(@PathVariable UUID id, @RequestBody WorkOrderItemDTO workOrderItemDTO) {
        ResponseApi<WorkOrderResumeDTO> responseApi = updateWorkOrderItemsUseCase.execute(id, workOrderItemDTO);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
            summary = "Busca o histórico de ordens de serviço por CPF",
            description = "Endpoint para buscar o histórico de ordens de serviço pelo CPF do cliente")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Histórico de ordens de serviço encontrado com sucesso.") })
    @GetMapping("/cpf/{cpf}/latest-work-order-history")
    public ResponseEntity<ResponseApi<List<WorkOrderWithHistoryResponseDTO>>> getHistoryByCpf(@PathVariable String cpf) {
        ResponseApi<List<WorkOrderWithHistoryResponseDTO>> responseApi = getWorkOrderHistoryByCpfUseCase.execute(cpf);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
            summary = "Calcula o tempo médio de conclusão das ordens de serviço",
            description = "Endpoint para calcular o tempo médio de conclusão das ordens de serviço")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Tempo médio calculado com sucesso.") })
    @GetMapping("/calculate-avarage-time")
    public ResponseEntity<String> calculateAvarageTime() {
        ResponseApi<List<WorkOrderAvarageTime>> responseApi = findAvarageTimeWorkOrderUseCase.executeList();
        HttpStatus status = HttpStatus.valueOf(responseApi.getStatus().name());
        if (responseApi.getStatus().is4xxClientError()) return ResponseEntity.status(status).body(responseApi.getMessage());
        List<WorkOrderAvarageTime> allAvarageTimes  = responseApi.getData();
        Duration avarageTimeMessage = allAvarageTimes.stream()
                .map(WorkOrderAvarageTime::avarageTime)
                .reduce(Duration.ZERO, Duration::plus)
                .dividedBy(allAvarageTimes.size());

        return ResponseEntity.ok(
                String.format("%02d:%02d",
                avarageTimeMessage.toHoursPart(),
                avarageTimeMessage.toMinutesPart())
                );
    }
}
