package com.fiap.challenge.customers.useCases.find;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.shared.model.ResponseApi;

public interface FindCustomersByIdsUseCase {
    ResponseApi<List<CustomerResponseDTO>> execute(List<UUID> ids);
}