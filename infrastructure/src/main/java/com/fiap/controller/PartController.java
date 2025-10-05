package com.fiap.controller;

import com.fiap.core.domain.part.Part;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.dto.part.CreatePartRequest;
import com.fiap.dto.part.PartResponse;
import com.fiap.dto.part.UpdatePartRequest;
import com.fiap.mapper.part.PartMapper;
import com.fiap.usecase.part.CreatePartUseCase;
import com.fiap.usecase.part.DeletePartUseCase;
import com.fiap.usecase.part.FindPartByIdUseCase;
import com.fiap.usecase.part.UpdatePartUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/parts")
public class PartController {

    private final CreatePartUseCase createPartUseCase;
    private final FindPartByIdUseCase findPartByIdUseCase;
    private final UpdatePartUseCase updatePartUseCase;
    private final DeletePartUseCase deletePartUseCase;
    private final PartMapper partMapper;

    public PartController(CreatePartUseCase createPartUseCase, FindPartByIdUseCase findPartByIdUseCase, UpdatePartUseCase updatePartUseCase, DeletePartUseCase deletePartUseCase, PartMapper partMapper) {
        this.createPartUseCase = createPartUseCase;
        this.findPartByIdUseCase = findPartByIdUseCase;
        this.updatePartUseCase = updatePartUseCase;
        this.deletePartUseCase = deletePartUseCase;
        this.partMapper = partMapper;
    }

    @Operation(summary = "Cria uma nova peça")
    @ApiResponse(responseCode = "201", description = "Peça criada com sucesso")
    @PostMapping
    public ResponseEntity<PartResponse> createPart(@Valid @RequestBody CreatePartRequest request) throws BusinessRuleException {
        Part newPart = createPartUseCase.execute(partMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(partMapper.toResponse(newPart));
    }

    @Operation(summary = "Busca uma peça pelo ID")
    @ApiResponse(responseCode = "200", description = "Peça encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<PartResponse> findPartById(@PathVariable UUID id) throws NotFoundException {
        Part part = findPartByIdUseCase.execute(id);
        return ResponseEntity.ok(partMapper.toResponse(part));
    }

    @Operation(summary = "Atualiza uma peça existente")
    @ApiResponse(responseCode = "200", description = "Peça atualizada com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<PartResponse> updatePart(@PathVariable UUID id, @Valid @RequestBody UpdatePartRequest request) throws NotFoundException, BusinessRuleException {
        Part partToUpdate = partMapper.toDomain(id, request);
        Part updatedPart = updatePartUseCase.execute(partToUpdate);
        return ResponseEntity.ok(partMapper.toResponse(updatedPart));
    }

    @Operation(summary = "Deleta uma peça pelo ID")
    @ApiResponse(responseCode = "204", description = "Peça deletada com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable UUID id) throws NotFoundException, BusinessRuleException {
        deletePartUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}