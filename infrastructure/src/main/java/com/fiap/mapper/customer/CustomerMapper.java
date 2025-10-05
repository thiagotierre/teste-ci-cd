package com.fiap.mapper.customer;

import com.fiap.core.domain.customer.Customer;
import com.fiap.core.domain.customer.DocumentNumber;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.dto.customer.CreateCustomerRequest;
import com.fiap.dto.customer.CustomerResponse;
import com.fiap.dto.customer.UpdateCustomerRequest;
import com.fiap.persistence.entity.customer.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(Customer customer) {
        return new CustomerEntity(
                customer.getId(),
                customer.getName(),
                customer.getDocumentNumber().getValue(),
                customer.getPhone(),
                customer.getEmail(),
                new ArrayList<>(),
                new ArrayList<>(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

    public Customer toDomain(CustomerEntity customerEntity) {
        return new Customer(
                customerEntity.getId(),
                customerEntity.getName(),
                DocumentNumber.fromPersistence(customerEntity.getDocumentNumber()),
                customerEntity.getPhone(),
                customerEntity.getEmail(),
                customerEntity.getCreatedAt(),
                customerEntity.getUpdatedAt()
        );
    }

    public Customer toDomain(CreateCustomerRequest request) throws DocumentNumberException {
        return new Customer(
                request.name(),
                DocumentNumber.of(request.documentNumber()),
                request.phone(),
                request.email()
        );
    }

    public Customer toDomain(UUID id, UpdateCustomerRequest request) throws DocumentNumberException {
        return new Customer(
                id,
                request.name(),
                DocumentNumber.of(request.documentNumber()),
                request.phone(),
                request.email()
        );
    }

    public Customer toDomain(UUID id) {
        return new Customer(
                id
        );
    }

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail(), customer.getDocumentNumber());
    }
}