package com.fiap.challenge.services.useCases.find;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.services.entity.ServiceModel;
import com.fiap.challenge.services.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindServicesByIdsUseCaseImpl implements FindServicesByIdsUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public ResponseApi<List<ServiceResponseDTO>> execute(List<UUID> ids) {
        ResponseApi<List<ServiceResponseDTO>> responseApi = new ResponseApi<>();
        List<ServiceModel> foundServices = serviceRepository.findAllById(ids);

        return responseApi.of(HttpStatus.OK, "Serviços encontrados com sucesso",
                foundServices.stream()
                .map(this::convertToDto)
                .toList());
    }

    private ServiceResponseDTO convertToDto(ServiceModel model) {
        return new ServiceResponseDTO(
                model.getId(),
                model.getName(),
                model.getDescription(),
                model.getBasePrice(),
                model.getEstimatedTimeMin(),
                model.getCreatedAt(),
                model.getUpdatedAt()
        );
    }
}