package com.fiap.challenge.customers.useCases.update;

import java.util.UUID;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.users.dto.UpdateCustomerRequestDTO;

public interface UpdateCustomerUseCase {
    ResponseApi<CustomerResponseDTO> execute(UUID id, UpdateCustomerRequestDTO request);
}