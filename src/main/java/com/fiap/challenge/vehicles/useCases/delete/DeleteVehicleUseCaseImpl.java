package com.fiap.challenge.vehicles.useCases.delete;

import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.shared.exception.vehicle.VehicleNotFoundException;
import com.fiap.challenge.vehicles.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteVehicleUseCaseImpl implements DeleteVehicleUseCase {

    private final VehicleRepository vehicleRepository;

    @Override
    public ResponseApi<Void> execute(UUID id) {
        ResponseApi<Void> responseApi = new ResponseApi<>();
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException(id);
        }

        vehicleRepository.deleteById(id);
        return responseApi.of(HttpStatus.NO_CONTENT, "Veículo deletado com sucesso!");
    }
}