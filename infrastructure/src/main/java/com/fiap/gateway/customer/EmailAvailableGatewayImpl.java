package com.fiap.gateway.customer;

import com.fiap.application.gateway.customer.EmailAvailableGateway;
import com.fiap.persistence.repository.customer.CustomerEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailAvailableGatewayImpl implements EmailAvailableGateway {

    private final CustomerEntityRepository customerEntityRepository;

    public EmailAvailableGatewayImpl(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }

    @Override
    public Boolean emailAvailable(String email) {
        return !customerEntityRepository.existsByEmail(email);
    }
}
