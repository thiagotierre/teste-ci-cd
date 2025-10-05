package com.fiap.challenge.customers.useCases.create;

import com.fiap.challenge.customers.dto.CreateCustomerRequestDTO;
import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.shared.model.ResponseApi;

public interface CreateCustomerUseCase {
    ResponseApi<CustomerResponseDTO> execute(CreateCustomerRequestDTO request);
}