package com.fiap.challenge.vehicles.useCases.find;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerInfo;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindVehiclesByIdsUseCaseImpl implements FindVehiclesByIdsUseCase {

    private final VehicleRepository vehicleRepository;

    @Override
    public ResponseApi<List<VehicleResponseDTO>> execute(List<UUID> ids) {
        ResponseApi<List<VehicleResponseDTO>> responseApi = new ResponseApi<>();
        return responseApi.of(HttpStatus.OK, "Veículos encontrados com sucesso!",
                vehicleRepository.findAllById(ids).stream()
                .map(this::convertToDto)
                .toList());
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