package com.fiap.controller;

import com.fiap.core.exception.EmailException;
import com.fiap.core.exception.InternalServerErrorException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.dto.user.CreateUserRequest;
import com.fiap.dto.user.UpdateUserRequest;
import com.fiap.dto.user.UserResponse;
import com.fiap.mapper.user.UserMapper;
import com.fiap.usecase.user.CreateUserUseCase;
import com.fiap.usecase.user.DeleteUserUseCase;
import com.fiap.usecase.user.FindUserByIdUseCase;
import com.fiap.usecase.user.UpdateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final UserMapper userMapper;

    public UserController(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase, FindUserByIdUseCase findUserByIdUseCase, UserMapper userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.userMapper = userMapper;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    @Operation(
            summary = "Cria um novo usuário",
            description = "Endpoint para criar um novo usuário.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso.") })
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) throws EmailException, InternalServerErrorException {
        var user = createUserUseCase.execute(userMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(user));
    }

    @Operation(
            summary = "Atualiza um usuário existente",
            description = "Endpoint para atualizar um usuário.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
                      @ApiResponse(responseCode = "404", description = "Usuário não encontrado.") })
    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request) throws EmailException, InternalServerErrorException, NotFoundException {
        var user = updateUserUseCase.execute(userMapper.toDomainUpdate(request));
        return ResponseEntity.ok().body(userMapper.toResponse(user));
    }

    @Operation(
            summary = "Deleta um usuário pelo ID",
            description = "Endpoint para deletar um usuário pelo ID.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso."),
                      @ApiResponse(responseCode = "404", description = "Usuário não encontrado.") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) throws NotFoundException {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Busca um usuário pelo ID",
            description = "Endpoint para buscar um usuário pelo ID.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
                      @ApiResponse(responseCode = "404", description = "Usuário não encontrado.") })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> FindUserById(@PathVariable UUID id) throws NotFoundException {
        var user = findUserByIdUseCase.execute(id);
        return ResponseEntity.ok().body(userMapper.toResponse(user.get()));
    }
}
