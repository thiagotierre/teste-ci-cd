package com.fiap.usecase.service;

import com.fiap.core.domain.service.Service;
import com.fiap.core.exception.NotFoundException;

public interface UpdateServiceUseCase {
    Service execute(Service service) throws NotFoundException;
}