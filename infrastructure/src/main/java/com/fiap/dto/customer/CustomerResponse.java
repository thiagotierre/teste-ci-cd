package com.fiap.dto.customer;

import com.fiap.core.domain.customer.DocumentNumber;

import java.util.UUID;

public record CustomerResponse (
        UUID id,
        String name,
        String email,
        DocumentNumber documentNumber) {
}
