package com.fiap.challenge.parts.useCases.update;

import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.dto.UpdatePartRequestDTO;
import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.shared.exception.part.PartNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePartUseCaseImpl implements UpdatePartUseCase {

    private final PartsRepository partRepository;

    @Override
    @Transactional
    public ResponseApi<PartResponseDTO> execute(UUID id, UpdatePartRequestDTO partRequest) {
        ResponseApi<PartResponseDTO> response = new ResponseApi<>();
        PartModel partToUpdate = partRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException(id));

        partToUpdate.setName(partRequest.name());
        partToUpdate.setDescription(partRequest.description());
        partToUpdate.setPrice(partRequest.price());
        partToUpdate.setStockQuantity(partRequest.stockQuantity());
        partToUpdate.setReservedStock(partRequest.reservedStock());
        partToUpdate.setMinimumStock(partRequest.minimumStock());

        PartModel updatedPart = partRepository.save(partToUpdate);

        return response.of(HttpStatus.OK, "Peça atualizada com sucesso",
                new PartResponseDTO(
            updatedPart.getId(),
            updatedPart.getName(),
            updatedPart.getDescription(),
            updatedPart.getPrice(),
            updatedPart.getStockQuantity(),
            updatedPart.getReservedStock(),
            updatedPart.getMinimumStock(),
            updatedPart.getCreatedAt(),
            updatedPart.getUpdatedAt()
        ));
    }
}