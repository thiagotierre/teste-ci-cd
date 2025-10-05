package com.fiap.application.usecaseimpl.vehicle;

import com.fiap.application.gateway.customer.CustomerGateway;
import com.fiap.application.gateway.vehicle.VehicleGateway;
import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.vehicle.FindVehicleByIdUseCase;
import com.fiap.usecase.vehicle.UpdateVehicleUseCase;

public class UpdateVehicleUseCaseImpl implements UpdateVehicleUseCase {

    private final VehicleGateway vehicleGateway;
    private final CustomerGateway customerGateway;
    private final FindVehicleByIdUseCase findVehicleByIdUseCase;

    public UpdateVehicleUseCaseImpl(VehicleGateway vehicleGateway, CustomerGateway customerGateway, FindVehicleByIdUseCase findVehicleByIdUseCase) {
        this.vehicleGateway = vehicleGateway;
        this.customerGateway = customerGateway;
        this.findVehicleByIdUseCase = findVehicleByIdUseCase;
    }

    @Override
    public Vehicle execute(Vehicle vehicle) throws NotFoundException, DocumentNumberException {
        Vehicle existingVehicle = findVehicleByIdUseCase.execute(vehicle.getId());

        customerGateway.findById(vehicle.getCustomer().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.CUST0001.getMessage(), ErrorCodeEnum.CUST0001.getCode()));

        existingVehicle.setCustomer(vehicle.getCustomer());
        existingVehicle.setLicensePlate(vehicle.getLicensePlate());
        existingVehicle.setBrand(vehicle.getBrand());
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setYear(vehicle.getYear());

        return vehicleGateway.update(existingVehicle);
    }
}