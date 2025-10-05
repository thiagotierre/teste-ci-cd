package com.fiap.challenge.parts.controller;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.parts.dto.CreatePartRequestDTO;
import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.dto.UpdatePartRequestDTO;
import com.fiap.challenge.parts.useCases.create.CreatePartUseCase;
import com.fiap.challenge.parts.useCases.delete.DeletePartUseCase;
import com.fiap.challenge.parts.useCases.find.FindPartByIdUseCase;
import com.fiap.challenge.parts.useCases.find.FindPartsByIdsUseCase;
import com.fiap.challenge.parts.useCases.update.UpdatePartUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/parts")
@Tag(name = "Peças", description = "Controlador para Peças.")
public class PartsController {

	private final CreatePartUseCase createPartUseCase;
	private final UpdatePartUseCase updatePartUseCase;
	private final FindPartByIdUseCase findPartByIdUseCase;
	private final FindPartsByIdsUseCase findPartsByIdsUseCase;
	private final DeletePartUseCase deletePartUseCase;

    @Operation(
        summary = "Cria uma nova peça",
        description = "Endpoint para criar uma nova peça.")
    @ApiResponse(responseCode = "201", description = "Peça criada com sucesso.")
	@PostMapping
	public ResponseEntity<ResponseApi<PartResponseDTO>> createPart(@RequestBody CreatePartRequestDTO cratePart) {
        ResponseApi<PartResponseDTO> responseApi = createPartUseCase.execute(cratePart);
		return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
	}

    @Operation(
        summary = "Atualiza uma peça existente",
        description = "Endpoint para atualizar uma peça pelo ID.")
    @ApiResponse(responseCode = "200", description = "Peça atualizada com sucesso.")
	@PutMapping("/{id}")
    public ResponseEntity<ResponseApi<PartResponseDTO>> updatePart(@PathVariable UUID id,@RequestBody UpdatePartRequestDTO requestDTO) {
        ResponseApi<PartResponseDTO> responseApi = updatePartUseCase.execute(id, requestDTO);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
        summary = "Busca uma peça pelo ID",
        description = "Endpoint para buscar uma peça pelo ID.")
    @ApiResponse(responseCode = "200", description = "Peça encontrada com sucesso.")
	@GetMapping("/{id}")
    public ResponseEntity<ResponseApi<PartResponseDTO>> findPartById(@PathVariable UUID id) {
        ResponseApi <PartResponseDTO> responseApi = findPartByIdUseCase.execute(id);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
        summary = "Busca várias peças pelos IDs",
        description = "Endpoint para buscar várias peças pelos IDs.")
    @ApiResponse(responseCode = "200", description = "Peças encontradas com sucesso.")
    @GetMapping
    public ResponseEntity<ResponseApi<List<PartResponseDTO>>> findPartsByIds(@RequestParam List<UUID> ids) {
        ResponseApi<List<PartResponseDTO>> responseApi = findPartsByIdsUseCase.execute(ids);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
        summary = "Deleta uma peça pelo ID",
        description = "Endpoint para deletar uma peça pelo ID.")
    @ApiResponse(responseCode = "200", description = "Peça deletada com sucesso.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi<Void>> delete(@PathVariable UUID id) {
        ResponseApi<Void> responseApi = deletePartUseCase.execute(id);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }
}
