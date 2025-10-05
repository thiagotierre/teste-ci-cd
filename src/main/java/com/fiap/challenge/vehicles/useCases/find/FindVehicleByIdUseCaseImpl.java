package com.fiap.challenge.vehicles.useCases.find;

import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerInfo;
import com.fiap.challenge.shared.exception.vehicle.VehicleNotFoundException;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindVehicleByIdUseCaseImpl implements FindVehicleByIdUseCase {

    private final VehicleRepository vehicleRepository;

    @Override
    @Transactional
    public ResponseApi<VehicleResponseDTO> execute(UUID id) {
        ResponseApi<VehicleResponseDTO> responseApi = new ResponseApi<>();

        return responseApi.of(HttpStatus.OK,"Veículo encontrado com sucesso!",
                vehicleRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new VehicleNotFoundException(id)));
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