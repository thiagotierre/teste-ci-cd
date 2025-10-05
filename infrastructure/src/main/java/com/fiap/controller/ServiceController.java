package com.fiap.controller;

import com.fiap.core.domain.service.Service;
import com.fiap.core.exception.NotFoundException;
import com.fiap.dto.service.CreateServiceRequest;
import com.fiap.dto.service.ServiceResponse;
import com.fiap.dto.service.UpdateServiceRequest;
import com.fiap.mapper.service.ServiceMapper;
import com.fiap.usecase.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/services")
public class ServiceController {

    private final CreateServiceUseCase createServiceUseCase;
    private final FindServiceByIdUseCase findServiceByIdUseCase;
    private final FindServicesByIdsUseCase findServicesByIdsUseCase;
    private final UpdateServiceUseCase updateServiceUseCase;
    private final DeleteServiceUseCase deleteServiceUseCase;
    private final ServiceMapper serviceMapper;

    public ServiceController(CreateServiceUseCase createServiceUseCase, FindServiceByIdUseCase findServiceByIdUseCase, FindServicesByIdsUseCase findServicesByIdsUseCase, UpdateServiceUseCase updateServiceUseCase, DeleteServiceUseCase deleteServiceUseCase, ServiceMapper serviceMapper) {
        this.createServiceUseCase = createServiceUseCase;
        this.findServiceByIdUseCase = findServiceByIdUseCase;
        this.findServicesByIdsUseCase = findServicesByIdsUseCase;
        this.updateServiceUseCase = updateServiceUseCase;
        this.deleteServiceUseCase = deleteServiceUseCase;
        this.serviceMapper = serviceMapper;
    }

    @Operation(summary = "Cria um novo serviço")
    @ApiResponse(responseCode = "201", description = "Serviço criado")
    @PostMapping
    public ResponseEntity<ServiceResponse> createService(@Valid @RequestBody CreateServiceRequest request) {
        Service newService = createServiceUseCase.execute(serviceMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceMapper.toResponse(newService));
    }

    @Operation(summary = "Busca um serviço pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> findServiceById(@PathVariable UUID id) throws NotFoundException {
        Service service = findServiceByIdUseCase.execute(id);
        return ResponseEntity.ok(serviceMapper.toResponse(service));
    }

    @Operation(summary = "Busca uma lista de serviços por IDs")
    @GetMapping
    public ResponseEntity<List<ServiceResponse>> findServicesByIds(@RequestParam List<UUID> ids) {
        List<Service> services = findServicesByIdsUseCase.execute(ids);
        return ResponseEntity.ok(services.stream().map(serviceMapper::toResponse).collect(Collectors.toList()));
    }

    @Operation(summary = "Atualiza um serviço")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateService(@PathVariable UUID id, @Valid @RequestBody UpdateServiceRequest request) throws NotFoundException {
        Service updatedService = updateServiceUseCase.execute(serviceMapper.toDomain(id, request));
        return ResponseEntity.ok(serviceMapper.toResponse(updatedService));
    }

    @Operation(summary = "Deleta um serviço")
    @ApiResponse(responseCode = "204", description = "Serviço deletado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable UUID id) throws NotFoundException {
        deleteServiceUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}