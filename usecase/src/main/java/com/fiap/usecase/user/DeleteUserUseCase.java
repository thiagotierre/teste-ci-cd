package com.fiap.usecase.user;

import com.fiap.core.exception.NotFoundException;

import java.util.UUID;

public interface DeleteUserUseCase {
    void execute(UUID id) throws NotFoundException;
}
