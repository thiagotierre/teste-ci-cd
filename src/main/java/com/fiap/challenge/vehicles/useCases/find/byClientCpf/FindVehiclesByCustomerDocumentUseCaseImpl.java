package com.fiap.challenge.vehicles.useCases.find.byClientCpf;

import java.util.List;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerInfo;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindVehiclesByCustomerDocumentUseCaseImpl implements FindVehiclesByCustomerDocumentUseCase {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ResponseApi<List<VehicleResponseDTO>> execute(String cpfCnpj) {
        ResponseApi<List<VehicleResponseDTO>> responseApi = new ResponseApi<>();
        if (!customerRepository.existsByCpfCnpj(cpfCnpj)) {
            throw new CustomerNotFoundException("No client found with CPF/CNPJ: " + cpfCnpj);
        }

        return responseApi.of(HttpStatus.OK, "Vehicles found successfully!",
                vehicleRepository.findByCustomerCpfCnpj(cpfCnpj)
                .stream()
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