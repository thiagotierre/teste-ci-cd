package com.fiap.challenge.users.usecases.create;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.users.dto.CreateUserRequestDTO;
import com.fiap.challenge.users.dto.UserResponseDTO;

public interface CreateUserUseCase {

	ResponseApi<UserResponseDTO> execute(CreateUserRequestDTO requestDTO);
}