package com.fiap.application.usecaseimpl.customer;

import com.fiap.application.gateway.customer.CustomerGateway;
import com.fiap.core.domain.customer.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.customer.FindCustomerByDocumentUseCase;

import java.util.Optional;

public class FindCustomerByDocumentUseCaseImpl implements FindCustomerByDocumentUseCase {

    private final CustomerGateway customerGateway;

    public FindCustomerByDocumentUseCaseImpl(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    @Override
    public Customer execute(String documentNumber) throws DocumentNumberException, NotFoundException {
        Optional<Customer> customer = customerGateway.findByDocumentNumber(documentNumber);

        if (customer.isEmpty()) {
            throw new NotFoundException(ErrorCodeEnum.CUST0001.getMessage(), ErrorCodeEnum.CUST0001.getCode());
        }

        return customer.get();
    }
}
