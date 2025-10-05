package com.fiap.application.usecaseimpl.customer;

import com.fiap.application.gateway.customer.CustomerGateway;
import com.fiap.core.domain.customer.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.EmailException;
import com.fiap.core.exception.InternalServerErrorException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.customer.CreateCustomerUseCase;
import com.fiap.usecase.customer.DocumentNumberAvailableUseCase;
import com.fiap.usecase.customer.EmailAvailableUseCase;

import java.util.Objects;

public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

    private final DocumentNumberAvailableUseCase documentNumberAvailableUseCase;
    private final EmailAvailableUseCase emailAvailableUseCase;
    private final CustomerGateway customerGateway;

    public CreateCustomerUseCaseImpl(DocumentNumberAvailableUseCase documentNumberAvailableUseCase, EmailAvailableUseCase emailAvailableUseCase,
                                     CustomerGateway customerGateway) {
        this.documentNumberAvailableUseCase = documentNumberAvailableUseCase;
        this.emailAvailableUseCase = emailAvailableUseCase;
        this.customerGateway = customerGateway;
    }

    @Override
    public Customer execute(Customer customer) throws DocumentNumberException, EmailException, InternalServerErrorException {
        if (!documentNumberAvailableUseCase.documentNumberAvailable(customer.getDocumentNumber().getValue())) {
            throw new DocumentNumberException(ErrorCodeEnum.CAD0002.getMessage(), ErrorCodeEnum.CAD0002.getCode());
        }

        if (!emailAvailableUseCase.emailAvailable(customer.getEmail())) {
            throw new EmailException(ErrorCodeEnum.CAD0003.getMessage(), ErrorCodeEnum.CAD0003.getCode());
        }

        Customer customerCreated = customerGateway.create(customer);
        if (Objects.isNull(customerCreated)) {
            throw new InternalServerErrorException(ErrorCodeEnum.CAD0004.getMessage(), ErrorCodeEnum.CAD0004.getCode());
        }

        return customerCreated;
    }
}
