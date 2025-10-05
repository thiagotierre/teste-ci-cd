package com.fiap.challenge.services.useCases.update;

import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.services.dto.InputServiceDTO;
import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.shared.exception.serice.ServiceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateServiceUseCaseImpl implements UpdateServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public ResponseApi<ServiceResponseDTO> execute(UUID id, InputServiceDTO updateServiceDTO) {
        ResponseApi<ServiceResponseDTO> responseApi = new ResponseApi<>();
        var serviceModel = this.serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        serviceModel.setName(updateServiceDTO.name());
        serviceModel.setDescription(updateServiceDTO.description());
        serviceModel.setBasePrice(updateServiceDTO.basePrice());
        serviceModel.setEstimatedTimeMin(updateServiceDTO.estimatedTimeMin());

        var updatedService = this.serviceRepository.save(serviceModel);

        return responseApi.of(HttpStatus.OK, "Serviço atualizado com sucesso",
                new ServiceResponseDTO(
                updatedService.getId(),
                updatedService.getName(),
                updatedService.getDescription(),
                updatedService.getBasePrice(),
                updatedService.getEstimatedTimeMin(),
                updatedService.getCreatedAt(),
                updatedService.getUpdatedAt()
        ));
    }
}