package com.fiap.application.usecaseimpl.customer;

import com.fiap.application.gateway.customer.CustomerGateway;
import com.fiap.core.domain.customer.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.EmailException;
import com.fiap.core.exception.InternalServerErrorException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.customer.DocumentNumberAvailableUseCase;
import com.fiap.usecase.customer.EmailAvailableUseCase;
import com.fiap.usecase.customer.UpdateCustomerUseCase;

import java.util.Objects;

public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {

    private final DocumentNumberAvailableUseCase documentNumberAvailableUseCase;
    private final EmailAvailableUseCase emailAvailableUseCase;
    private final CustomerGateway customerGateway;

    public UpdateCustomerUseCaseImpl(DocumentNumberAvailableUseCase documentNumberAvailableUseCase, EmailAvailableUseCase emailAvailableUseCase,
                                     CustomerGateway customerGateway) {
        this.documentNumberAvailableUseCase = documentNumberAvailableUseCase;
        this.emailAvailableUseCase = emailAvailableUseCase;
        this.customerGateway = customerGateway;
    }

    @Override
    public Customer execute(Customer customer) throws DocumentNumberException, NotFoundException, EmailException, InternalServerErrorException {
        Customer oldCustomer = customerGateway.findById(customer.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.CUST0001.getMessage(), ErrorCodeEnum.CUST0001.getCode()));

        if (!oldCustomer.getDocumentNumber().equals(customer.getDocumentNumber()) &&
                !documentNumberAvailableUseCase.documentNumberAvailable(customer.getDocumentNumber().getValue())) {
            throw new DocumentNumberException(ErrorCodeEnum.CAD0002.getMessage(), ErrorCodeEnum.CAD0002.getCode());
        }

        if (!oldCustomer.getEmail().equals(customer.getEmail()) &&
                !emailAvailableUseCase.emailAvailable(customer.getEmail())) {
            throw new EmailException(ErrorCodeEnum.CAD0003.getMessage(), ErrorCodeEnum.CAD0003.getCode());
        }

        Customer customerUpdated = customerGateway.update(customer);
        if (Objects.isNull(customerUpdated)) {
            throw new InternalServerErrorException(ErrorCodeEnum.CAD0004.getMessage(), ErrorCodeEnum.CAD0004.getCode());
        }

        return customerUpdated;
    }
}
