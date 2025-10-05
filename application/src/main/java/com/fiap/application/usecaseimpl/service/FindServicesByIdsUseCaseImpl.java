package com.fiap.application.usecaseimpl.service;

import com.fiap.application.gateway.service.ServiceGateway;
import com.fiap.core.domain.service.Service;
import com.fiap.usecase.service.FindServicesByIdsUseCase;
import java.util.List;
import java.util.UUID;

public class FindServicesByIdsUseCaseImpl implements FindServicesByIdsUseCase {

    private final ServiceGateway serviceGateway;

    public FindServicesByIdsUseCaseImpl(ServiceGateway serviceGateway) {
        this.serviceGateway = serviceGateway;
    }

    @Override
    public List<Service> execute(List<UUID> ids) {
        return serviceGateway.findByIds(ids);
    }
}