package com.fiap.usecase.customer;

import com.fiap.core.domain.customer.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.EmailException;
import com.fiap.core.exception.InternalServerErrorException;

public interface CreateCustomerUseCase {

    Customer execute(Customer customer) throws DocumentNumberException, EmailException, InternalServerErrorException;
}
