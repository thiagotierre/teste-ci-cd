package com.fiap.challenge.customers.useCases.find;

import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindCustomerByIdUseCaseImpl implements FindCustomerByIdUseCase {

    private final CustomerRepository customerRepository;

    @Override
    public ResponseApi<CustomerResponseDTO> execute(UUID id) {
        ResponseApi<CustomerResponseDTO> responseApi = new ResponseApi<>();
    customerRepository
        .findById(id)
        .map(
            customer ->
                new CustomerResponseDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getCpfCnpj(),
                    customer.getPhone(),
                    customer.getEmail(),
                    customer.getCreatedAt(),
                    customer.getUpdatedAt()))
        .ifPresentOrElse(customerResponseDTO -> responseApi.of(HttpStatus.OK, "Customer found successfully.", customerResponseDTO),
            () -> responseApi.of(HttpStatus.NOT_FOUND, "Customer not found with ID: " + id, null));
                return responseApi;
    }
}