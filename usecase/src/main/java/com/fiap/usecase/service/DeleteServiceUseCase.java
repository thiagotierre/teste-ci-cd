package com.fiap.usecase.service;

import com.fiap.core.exception.NotFoundException;
import java.util.UUID;

public interface DeleteServiceUseCase {
    void execute(UUID id) throws NotFoundException;
}