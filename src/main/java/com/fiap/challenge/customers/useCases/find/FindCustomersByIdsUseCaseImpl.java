package com.fiap.challenge.customers.useCases.find;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindCustomersByIdsUseCaseImpl implements FindCustomersByIdsUseCase {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseApi<List<CustomerResponseDTO>> execute(List<UUID> ids) {
        ResponseApi<List<CustomerResponseDTO>> responseApi = new ResponseApi<>();
        return responseApi.of(HttpStatus.OK, "Clientes encontrados com sucesso",
                customerRepository.findAllById(ids).stream()
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