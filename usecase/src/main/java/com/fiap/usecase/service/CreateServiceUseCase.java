package com.fiap.usecase.service;

import com.fiap.core.domain.service.Service;

public interface CreateServiceUseCase {
    Service execute(Service service);
}