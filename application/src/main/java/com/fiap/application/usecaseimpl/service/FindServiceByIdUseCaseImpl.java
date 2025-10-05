package com.fiap.application.usecaseimpl.service;

import com.fiap.application.gateway.service.ServiceGateway;
import com.fiap.core.domain.service.Service;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.service.FindServiceByIdUseCase;
import java.util.UUID;

public class FindServiceByIdUseCaseImpl implements FindServiceByIdUseCase {

    private final ServiceGateway serviceGateway;

    public FindServiceByIdUseCaseImpl(ServiceGateway serviceGateway) {
        this.serviceGateway = serviceGateway;
    }

    @Override
    public Service execute(UUID id) throws NotFoundException {
        return serviceGateway.findById(id)
                .orElseThrow(() -> new NotFoundException("Service with id: " + id + " not found.", "SERVICE-404"));
    }
}