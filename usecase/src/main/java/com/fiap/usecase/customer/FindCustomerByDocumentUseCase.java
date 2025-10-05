package com.fiap.usecase.customer;

import com.fiap.core.domain.customer.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;

public interface FindCustomerByDocumentUseCase {

    Customer execute(String documentNumber) throws DocumentNumberException, NotFoundException;
}