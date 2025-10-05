package com.fiap.usecase.customer;

import com.fiap.core.domain.customer.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;

import java.util.UUID;

public interface FindCustomerByIdUseCase {

    Customer execute(UUID customerId) throws NotFoundException, DocumentNumberException;
}
