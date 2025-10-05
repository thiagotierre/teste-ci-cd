package com.fiap.challenge.users.dto;

import com.fiap.challenge.users.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDTO(
    @NotBlank String name,
    @NotBlank @Email String email,
    @NotBlank String password,
    @NotNull UserRole role
) {}