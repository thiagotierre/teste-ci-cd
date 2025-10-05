package com.fiap.challenge.vehicles.mapper;

import com.fiap.challenge.vehicles.dto.VehicleResumeDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public VehicleResumeDTO toVehicleResumeDTO(VehicleModel vehicle) {
        return new VehicleResumeDTO(
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getYear()
        );
    }
}
