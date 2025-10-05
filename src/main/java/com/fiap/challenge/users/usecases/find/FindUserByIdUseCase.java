package com.fiap.challenge.users.usecases.find;

import com.fiap.challenge.users.entity.UserModel;

import java.util.UUID;

public interface FindUserByIdUseCase {

    public UserModel execute(UUID userId);
}
