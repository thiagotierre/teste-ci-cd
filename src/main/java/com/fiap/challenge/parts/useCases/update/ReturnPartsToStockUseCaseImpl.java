package com.fiap.challenge.parts.useCases.update;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.dto.UpdatePartRequestDTO;
import com.fiap.challenge.parts.useCases.find.FindPartByIdUseCase;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReturnPartsToStockUseCaseImpl implements ReturnPartsToStockUseCase {

    private final FindPartByIdUseCase findPartByIdUseCase;
    private final UpdatePartUseCase updatePartUseCase;

    @Override
    public boolean execute(UUID partId, int quantity) {
        var status = false;
        var part = findPartByIdUseCase.execute(partId).getData();
        if (Objects.isNull(part)) throw new EntityNotFoundException("Part not found with ID: " + partId);
        if (part.reservedStock() >= quantity) {
            updatePartUseCase.execute(partId, addStockAndSubtractReservedStock(part, quantity));
            status = true;
        }
        return status;
    }

    private UpdatePartRequestDTO addStockAndSubtractReservedStock(PartResponseDTO part, int quantity) {
        return UpdatePartRequestDTO.builder()
                .name(part.name())
                .description(part.description())
                .price(part.price())
                .reservedStock(part.reservedStock() - quantity)
                .stockQuantity(part.stockQuantity() + quantity)
                .minimumStock(part.minimumStock())
                .build();
    }
}
