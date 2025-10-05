package com.fiap.challenge.customers.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CustomerResponseDTO(
    UUID id,
    String name,
    String cpfCnpj,
    String phone,
    String email,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}