package com.fiap.challenge.customers.useCases.find;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.shared.model.ResponseApi;

public interface FindCustomerByCpfCnpj {
    ResponseApi<CustomerResponseDTO> execute(String cpfCnpj);
}