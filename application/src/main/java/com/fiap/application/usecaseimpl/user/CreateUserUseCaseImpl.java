package com.fiap.application.usecaseimpl.user;

import com.fiap.application.gateway.user.UserGateway;
import com.fiap.core.domain.user.User;
import com.fiap.core.exception.InternalServerErrorException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.user.CreateUserUseCase;

import java.util.Objects;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    final UserGateway userGateway;

    public CreateUserUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;

    }

    @Override
    public User execute(User user) throws InternalServerErrorException {
        User userCreated = userGateway.create(user);
        if(Objects.isNull(userCreated)) {
          throw new InternalServerErrorException(
              ErrorCodeEnum.USE0008.getMessage(), ErrorCodeEnum.USE0008.getCode());
        }

        return userCreated;
    }
}
