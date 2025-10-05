package com.fiap.application.usecaseimpl.service;

import com.fiap.application.gateway.service.ServiceGateway;
import com.fiap.core.domain.service.Service;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.service.FindServiceByIdUseCase;
import com.fiap.usecase.service.UpdateServiceUseCase;

public class UpdateServiceUseCaseImpl implements UpdateServiceUseCase {

    private final ServiceGateway serviceGateway;
    private final FindServiceByIdUseCase findServiceByIdUseCase;

    public UpdateServiceUseCaseImpl(ServiceGateway serviceGateway, FindServiceByIdUseCase findServiceByIdUseCase) {
        this.serviceGateway = serviceGateway;
        this.findServiceByIdUseCase = findServiceByIdUseCase;
    }

    @Override
    public Service execute(Service service) throws NotFoundException {
        Service existingService = findServiceByIdUseCase.execute(service.getId());

        existingService.setName(service.getName());
        existingService.setDescription(service.getDescription());
        existingService.setBasePrice(service.getBasePrice());
        existingService.setEstimatedTimeMin(service.getEstimatedTimeMin());

        return serviceGateway.update(existingService);
    }
}