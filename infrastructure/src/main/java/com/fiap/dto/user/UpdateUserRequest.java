package com.fiap.dto.user;

import java.util.UUID;

public record UpdateUserRequest(UUID id, String name, String email, String role, String password) {}
