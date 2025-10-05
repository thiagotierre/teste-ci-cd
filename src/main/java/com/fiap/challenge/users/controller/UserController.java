package com.fiap.challenge.users.controller;

import com.fiap.challenge.shared.model.ResponseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.users.dto.CreateUserRequestDTO;
import com.fiap.challenge.users.dto.UserResponseDTO;
import com.fiap.challenge.users.usecases.create.CreateUserUseCase;
import com.fiap.challenge.users.usecases.create.CreateUserUseCaseImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Controlador para gerenciamento de usuários.")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCaseImpl createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @Operation(
        summary = "Cria um novo usuário",
        description = "Endpoint para criar um novo usuário no sistema.")
    @ApiResponse(
        responseCode = "201",
        description = "Usuário criado com sucesso.")
    @PostMapping
    public ResponseEntity<ResponseApi<UserResponseDTO>> createUser(@RequestBody @Valid CreateUserRequestDTO requestDTO) {
        ResponseApi<UserResponseDTO> responseApi = createUserUseCase.execute(requestDTO);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }
}