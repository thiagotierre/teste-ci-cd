package com.fiap.application.usecaseimpl.vehicle;

import com.fiap.application.gateway.customer.CustomerGateway;
import com.fiap.application.gateway.vehicle.VehicleGateway;
import com.fiap.core.domain.customer.Customer;
import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.vehicle.CreateVehicleUseCase;

public class CreateVehicleUseCaseImpl implements CreateVehicleUseCase {

    private final VehicleGateway vehicleGateway;
    private final CustomerGateway customerGateway;

    public CreateVehicleUseCaseImpl(VehicleGateway vehicleGateway, CustomerGateway customerGateway) {
        this.vehicleGateway = vehicleGateway;
        this.customerGateway = customerGateway;
    }

    @Override
    public Vehicle execute(Vehicle vehicle) throws NotFoundException, DocumentNumberException {
        Customer customer = customerGateway.findById(vehicle.getCustomer().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.CUST0001.getMessage(), ErrorCodeEnum.CUST0001.getCode()));

        Vehicle vehicleCreated = vehicleGateway.create(vehicle);
        vehicleCreated.setCustomer(customer);

        return vehicleCreated;
    }
}