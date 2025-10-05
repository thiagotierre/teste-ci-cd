package com.fiap.application.usecaseimpl.user;

import com.fiap.application.gateway.user.UserGateway;
import com.fiap.core.domain.user.User;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.user.FindUserByIdUseCase;

import java.util.Optional;
import java.util.UUID;

public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    final UserGateway userGateway;

    public FindUserByIdUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public Optional<User> execute(UUID userId) throws NotFoundException {
        return userGateway.findById(userId);
    }
}
