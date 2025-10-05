package com.fiap.config;

import com.fiap.application.gateway.customer.CustomerGateway;
import com.fiap.application.gateway.vehicle.VehicleGateway;
import com.fiap.application.usecaseimpl.vehicle.*;
import com.fiap.usecase.customer.FindCustomerByIdUseCase;
import com.fiap.usecase.vehicle.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VehicleConfig {

    @Bean
    public CreateVehicleUseCase createVehicleUseCase(VehicleGateway gateway, CustomerGateway customerGateway) {
        return new CreateVehicleUseCaseImpl(gateway, customerGateway);
    }

    @Bean
    public UpdateVehicleUseCase updateVehicleUseCase(VehicleGateway gateway, CustomerGateway customerGateway, FindVehicleByIdUseCase findVehicleUseCase) {
        return new UpdateVehicleUseCaseImpl(gateway, customerGateway,  findVehicleUseCase);
    }

    @Bean
    public DeleteVehicleUseCase deleteVehicleUseCase(VehicleGateway gateway) {
        return new DeleteVehicleUseCaseImpl(gateway);
    }

    @Bean
    public FindVehicleByIdUseCase findVehicleByIdUseCase(VehicleGateway gateway) {
        return new FindVehicleByIdUseCaseImpl(gateway);
    }

    @Bean
    public FindVehicleByPlateUseCase findVehicleByPlateUseCase(VehicleGateway gateway) {
        return new FindVehicleByPlateUseCaseImpl(gateway);
    }
}