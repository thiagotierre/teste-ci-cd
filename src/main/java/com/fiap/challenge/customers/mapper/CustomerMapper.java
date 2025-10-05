package com.fiap.challenge.customers.mapper;

import com.fiap.challenge.customers.dto.CustomerResumeDTO;
import com.fiap.challenge.customers.entity.CustomerModel;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResumeDTO toCustomerResumeDTO(CustomerModel customer) {
        return new CustomerResumeDTO(
                customer.getName(),
                customer.getCpfCnpj(),
                customer.getPhone(),
                customer.getEmail());
    }
}
