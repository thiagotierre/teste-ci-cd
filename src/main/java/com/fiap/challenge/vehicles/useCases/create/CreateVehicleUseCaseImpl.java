package com.fiap.challenge.vehicles.useCases.create;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerInfo;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;
import com.fiap.challenge.vehicles.dto.InputVehicleDTO;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateVehicleUseCaseImpl implements CreateVehicleUseCase {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ResponseApi<VehicleResponseDTO> execute(InputVehicleDTO dto) {
        ResponseApi<VehicleResponseDTO> responseApi = new ResponseApi<>();
        var customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(dto.customerId()));

        var vehicle = VehicleModel.builder()
                .customer(customer)
                .licensePlate(dto.licensePlate())
                .brand(dto.brand())
                .model(dto.model())
                .year(dto.year())
                .build();

        var savedVehicle = vehicleRepository.save(vehicle);

        return responseApi.of(HttpStatus.CREATED, "Veículo criado com sucesso!", convertToDto(savedVehicle));
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