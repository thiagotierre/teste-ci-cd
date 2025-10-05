package com.fiap.application.usecaseimpl.customer;

import com.fiap.application.gateway.customer.CustomerGateway;
import com.fiap.core.domain.customer.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.customer.FindCustomerByIdUseCase;

import java.util.Optional;
import java.util.UUID;

public class FindCustomerByIdUseCaseImpl implements FindCustomerByIdUseCase {

    private final CustomerGateway customerGateway;

    public FindCustomerByIdUseCaseImpl(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    public Customer execute(UUID customerId) throws NotFoundException, DocumentNumberException {

        Optional<Customer> customer = customerGateway.findById(customerId);

        if (customer.isEmpty()) {
            throw new NotFoundException(ErrorCodeEnum.CUST0001.getMessage(), ErrorCodeEnum.CUST0001.getCode());
        }

        return customer.get();
    }
}
