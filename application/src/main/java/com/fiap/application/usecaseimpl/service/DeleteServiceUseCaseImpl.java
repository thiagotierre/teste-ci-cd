package com.fiap.application.usecaseimpl.service;

import com.fiap.application.gateway.service.ServiceGateway;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.service.DeleteServiceUseCase;
import java.util.UUID;

public class DeleteServiceUseCaseImpl implements DeleteServiceUseCase {

    private final ServiceGateway serviceGateway;

    public DeleteServiceUseCaseImpl(ServiceGateway serviceGateway) {
        this.serviceGateway = serviceGateway;
    }

    @Override
    public void execute(UUID id) throws NotFoundException {
        if (!serviceGateway.existsById(id)) {
            throw new NotFoundException("Service with id: " + id + " not found.", "SERVICE-404");
        }
        serviceGateway.delete(id);
    }
}