package com.fiap.usecase.service;

import com.fiap.core.domain.service.Service;
import com.fiap.core.exception.NotFoundException;
import java.util.UUID;

public interface FindServiceByIdUseCase {
    Service execute(UUID id) throws NotFoundException;
}