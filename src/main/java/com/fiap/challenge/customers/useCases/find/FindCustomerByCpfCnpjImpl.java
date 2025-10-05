package com.fiap.challenge.customers.useCases.find;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindCustomerByCpfCnpjImpl implements FindCustomerByCpfCnpj {

    private final CustomerRepository customerRepository;

    @Override
    public ResponseApi<CustomerResponseDTO> execute(String cpfCnpj) {
        ResponseApi<CustomerResponseDTO> responseApi = new ResponseApi<>();
        return responseApi.of(HttpStatus.OK, "Cliente encontrado com sucesso",
                customerRepository.findByCpfCnpj(cpfCnpj)
                .map(customer -> new CustomerResponseDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getCpfCnpj(),
                        customer.getPhone(),
                        customer.getEmail(),
                        customer.getCreatedAt(),
                        customer.getUpdatedAt()
                ))
                .orElseThrow(() -> new CustomerNotFoundException(cpfCnpj)));
    }
}