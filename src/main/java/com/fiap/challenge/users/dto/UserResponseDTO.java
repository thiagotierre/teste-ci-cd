package com.fiap.challenge.users.dto;

import java.util.UUID;

import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.enums.UserRole;

public record UserResponseDTO(UUID id, String name, String email, UserRole role) {
    public UserResponseDTO(UserModel userModel) {
        this(userModel.getId(), userModel.getName(), userModel.getEmail(), userModel.getRole());
    }
}