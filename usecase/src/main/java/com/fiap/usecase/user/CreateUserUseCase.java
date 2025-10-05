package com.fiap.usecase.user;

import com.fiap.core.domain.user.User;
import com.fiap.core.exception.InternalServerErrorException;

public interface CreateUserUseCase {
    User execute(User user) throws InternalServerErrorException;
}
