package com.fiap.challenge.customers.controller;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
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

import com.fiap.challenge.customers.dto.CreateCustomerRequestDTO;
import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.useCases.create.CreateCustomerUseCase;
import com.fiap.challenge.customers.useCases.delete.DeleteCustomerUseCase;
import com.fiap.challenge.customers.useCases.find.FindCustomerByCpfCnpj;
import com.fiap.challenge.customers.useCases.find.FindCustomerByCpfCnpjLike;
import com.fiap.challenge.customers.useCases.find.FindCustomerByIdUseCase;
import com.fiap.challenge.customers.useCases.find.FindCustomersByIdsUseCase;
import com.fiap.challenge.customers.useCases.update.UpdateCustomerUseCase;
import com.fiap.challenge.users.dto.UpdateCustomerRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Controlador para Clientes.")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final FindCustomersByIdsUseCase findCustomersByIdsUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final FindCustomerByCpfCnpjLike findCustomerByCpfCnpjLike;
    private final FindCustomerByCpfCnpj findCustomerByCpfCnpj;

    @Operation(
        summary = "Cria um novo cliente",
        description = "Endpoint para criar um novo cliente.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso.") })
    @PostMapping
    public ResponseEntity<ResponseApi<CustomerResponseDTO>> create(@RequestBody @Valid CreateCustomerRequestDTO requestDTO) {
        ResponseApi<CustomerResponseDTO> responseApi = createCustomerUseCase.execute(requestDTO);
    	return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
        summary = "Atualiza um cliente existente",
        description = "Endpoint para atualizar um cliente pelo ID.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso.") })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi<CustomerResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid UpdateCustomerRequestDTO requestDTO) {
        ResponseApi<CustomerResponseDTO> responseApi = updateCustomerUseCase.execute(id, requestDTO);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
        summary = "Busca um cliente pelo ID",
        description = "Endpoint para buscar um cliente pelo ID.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso.") })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<CustomerResponseDTO>> findById(@PathVariable UUID id) {
        ResponseApi<CustomerResponseDTO> responseApi = findCustomerByIdUseCase.execute(id);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
        summary = "Busca clientes por IDs",
        description = "Endpoint para buscar clientes por uma lista de IDs.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso.") })
    @GetMapping("/findById")
    public ResponseEntity<ResponseApi<List<CustomerResponseDTO>>> findById(@RequestParam List<UUID> ids) {
        ResponseApi<List<CustomerResponseDTO>> responseApi = findCustomersByIdsUseCase.execute(ids);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
        summary = "Deleta um cliente pelo ID",
        description = "Endpoint para deletar um cliente pelo ID.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso.") })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi<Void>> delete(@PathVariable UUID id) {
        ResponseApi<Void> responseApi = deleteCustomerUseCase.execute(id);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
        summary = "Busca um cliente pelo CPF parcial.",
        description = "Endpoint para buscar um cliente pelo CPF parcial.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso.") })
    @GetMapping("/findByCpfCnpjLike/{cpfCnpj}")
    public ResponseEntity<ResponseApi<List<CustomerResponseDTO>>> findByCpfLike(@PathVariable String cpfCnpj) {
        ResponseApi<List<CustomerResponseDTO>> responseApi = findCustomerByCpfCnpjLike.execute(cpfCnpj);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }

    @Operation(
        summary = "Busca um cliente pelo CPF.",
        description = "Endpoint para buscar um cliente pelo CPF.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso.") })
    @GetMapping("/findByCpfCnpj/{cpfCnpj}")
    public ResponseEntity<ResponseApi<CustomerResponseDTO>> findByCpfCnpj(@PathVariable String cpfCnpj) {
        ResponseApi<CustomerResponseDTO> responseApi = findCustomerByCpfCnpj.execute(cpfCnpj);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }
}