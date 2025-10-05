package com.fiap.application.gateway.customer;

import com.fiap.core.domain.customer.Customer;
import com.fiap.core.exception.DocumentNumberException;

import java.util.Optional;
import java.util.UUID;

public interface CustomerGateway {
    Customer create(Customer customer) throws DocumentNumberException;
    Customer update(Customer customer) throws DocumentNumberException;
    Optional<Customer> findById(UUID customerId) throws DocumentNumberException;
    Optional<Customer> findByDocumentNumber(String documentNumber) throws DocumentNumberException;
    void delete(Customer customer);
}
