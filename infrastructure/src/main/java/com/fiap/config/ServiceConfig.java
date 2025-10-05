package com.fiap.config;

import com.fiap.application.gateway.service.ServiceGateway;
import com.fiap.application.usecaseimpl.service.*;
import com.fiap.usecase.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public CreateServiceUseCase createServiceUseCase(ServiceGateway gateway) {
        return new CreateServiceUseCaseImpl(gateway);
    }

    @Bean
    public UpdateServiceUseCase updateServiceUseCase(ServiceGateway gateway, FindServiceByIdUseCase findUseCase) {
        return new UpdateServiceUseCaseImpl(gateway, findUseCase);
    }

    @Bean
    public DeleteServiceUseCase deleteServiceUseCase(ServiceGateway gateway) {
        return new DeleteServiceUseCaseImpl(gateway);
    }

    @Bean
    public FindServiceByIdUseCase findServiceByIdUseCase(ServiceGateway gateway) {
        return new FindServiceByIdUseCaseImpl(gateway);
    }

    @Bean
    public FindServicesByIdsUseCase findServicesByIdsUseCase(ServiceGateway gateway) {
        return new FindServicesByIdsUseCaseImpl(gateway);
    }
}