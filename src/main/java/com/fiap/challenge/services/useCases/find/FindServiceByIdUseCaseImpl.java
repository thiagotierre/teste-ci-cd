package com.fiap.challenge.services.useCases.find;

import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.shared.exception.serice.ServiceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindServiceByIdUseCaseImpl implements FindServiceByIdUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public ResponseApi<ServiceResponseDTO> execute(UUID id) {
        ResponseApi<ServiceResponseDTO> responseApi = new ResponseApi<>();
        var serviceModel = this.serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        return responseApi.of(HttpStatus.OK, "Serviço encontrado com sucesso",
                new ServiceResponseDTO(
                serviceModel.getId(),
                serviceModel.getName(),
                serviceModel.getDescription(),
                serviceModel.getBasePrice(),
                serviceModel.getEstimatedTimeMin(),
                serviceModel.getCreatedAt(),
                serviceModel.getUpdatedAt()
        ));
    }
}