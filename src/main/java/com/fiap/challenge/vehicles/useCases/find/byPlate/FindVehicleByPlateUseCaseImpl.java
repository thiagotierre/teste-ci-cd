package com.fiap.challenge.vehicles.useCases.find.byPlate;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerInfo;
import com.fiap.challenge.shared.exception.vehicle.VehicleNotFoundException;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindVehicleByPlateUseCaseImpl implements FindVehicleByPlateUseCase {

    private final VehicleRepository vehicleRepository;

    @Override
    public ResponseApi<VehicleResponseDTO> execute(String plate) {
        ResponseApi<VehicleResponseDTO> responseApi = new ResponseApi<>();
        return responseApi.of(HttpStatus.OK, "Vehicle found successfully!",
                vehicleRepository.findByLicensePlateIgnoreCase(plate)
                .map(this::convertToDto)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle with plate " + plate + " not found.")));
    }

    private VehicleResponseDTO convertToDto(VehicleModel model) {
        var customerInfo = new CustomerInfo(model.getCustomer().getId(), model.getCustomer().getName());
        return new VehicleResponseDTO(
                model.getId(),
                customerInfo,
                model.getLicensePlate(),
                model.getBrand(),
                model.getModel(),
                model.getYear(),
                model.getCreatedAt(),
                model.getUpdatedAt()
        );
    }
}