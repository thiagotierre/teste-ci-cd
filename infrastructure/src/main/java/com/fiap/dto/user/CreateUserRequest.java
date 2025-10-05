package com.fiap.dto.user;

public record CreateUserRequest(String name, String email, String role, String password) {}
