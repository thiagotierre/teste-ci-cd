package com.fiap.challenge.customers.useCases.find;

import java.util.List;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.shared.model.ResponseApi;

public interface FindCustomerByCpfCnpjLike {

	ResponseApi<List<CustomerResponseDTO>> execute(String cpfCnpj);
}
