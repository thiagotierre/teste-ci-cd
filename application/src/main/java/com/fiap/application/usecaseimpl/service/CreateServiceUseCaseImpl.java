package com.fiap.application.usecaseimpl.service;

import com.fiap.application.gateway.service.ServiceGateway;
import com.fiap.core.domain.service.Service;
import com.fiap.usecase.service.CreateServiceUseCase;

public class CreateServiceUseCaseImpl implements CreateServiceUseCase {

    private final ServiceGateway serviceGateway;

    public CreateServiceUseCaseImpl(ServiceGateway serviceGateway) {
        this.serviceGateway = serviceGateway;
    }

    @Override
    public Service execute(Service service) {
        return serviceGateway.create(service);
    }
}