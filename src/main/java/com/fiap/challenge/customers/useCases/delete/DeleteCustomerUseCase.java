package com.fiap.challenge.customers.useCases.delete;

import com.fiap.challenge.shared.model.ResponseApi;

import java.util.UUID;

public interface DeleteCustomerUseCase {
    ResponseApi<Void> execute(UUID id);
}