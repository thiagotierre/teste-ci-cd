package com.fiap.config;

import com.fiap.application.gateway.user.UserGateway;
import com.fiap.application.usecaseimpl.user.CreateUserUseCaseImpl;
import com.fiap.application.usecaseimpl.user.DeleteUserUseCaseImpl;
import com.fiap.application.usecaseimpl.user.FindUserByIdUseCaseImpl;
import com.fiap.application.usecaseimpl.user.UpdateUserUseCaseImpl;
import com.fiap.usecase.user.CreateUserUseCase;
import com.fiap.usecase.user.DeleteUserUseCase;
import com.fiap.usecase.user.FindUserByIdUseCase;
import com.fiap.usecase.user.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway userGateway){
        return new CreateUserUseCaseImpl(userGateway) {
        };
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserGateway userGateway){
        return new UpdateUserUseCaseImpl(userGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserGateway userGateway){
        return new DeleteUserUseCaseImpl(userGateway);
    }

    @Bean
    public FindUserByIdUseCase findUserByIdUseCase(UserGateway userGateway){
        return new FindUserByIdUseCaseImpl(userGateway);
    }

}
