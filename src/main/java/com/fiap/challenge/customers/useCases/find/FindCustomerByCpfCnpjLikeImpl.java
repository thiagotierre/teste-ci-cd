package com.fiap.challenge.customers.useCases.find;

import java.util.List;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindCustomerByCpfCnpjLikeImpl implements FindCustomerByCpfCnpjLike {

	private final CustomerRepository customerRepository;

	public ResponseApi<List<CustomerResponseDTO>> execute(String cpfCnpj) {
		ResponseApi<List<CustomerResponseDTO>> responseApi = new ResponseApi<>();
		List<CustomerModel> customers = customerRepository.findByCpfCnpjContainingWithCustomSort(cpfCnpj);

		return responseApi.of(HttpStatus.OK, "Clientes encontrados com sucesso",
				customers.stream()
				.map(customer -> new CustomerResponseDTO(
						customer.getId(),
						customer.getName(),
						customer.getCpfCnpj(),
						customer.getPhone(),
						customer.getEmail(),
						customer.getCreatedAt(),
						customer.getUpdatedAt()
				)).toList());
	}
}