package com.fiap.usecase.user;

import com.fiap.core.domain.user.User;
import com.fiap.core.exception.NotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface FindUserByIdUseCase {
    Optional<User> execute(UUID userId) throws NotFoundException;
}
