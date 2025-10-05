package com.fiap.challenge.parts.useCases.find;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.repository.PartsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindPartsByIdsUseCaseImpl implements FindPartsByIdsUseCase {

    private final PartsRepository partRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseApi<List<PartResponseDTO>> execute(List<UUID> ids) {
        ResponseApi<List<PartResponseDTO>> responseApi = new ResponseApi<>();
        List<PartModel> foundParts = partRepository.findAllById(ids);

        return responseApi.of(HttpStatus.OK, "Peças encontradas com sucesso",
                foundParts.stream()
                .map(part -> new PartResponseDTO(
                        part.getId(),
                        part.getName(),
                        part.getDescription(),
                        part.getPrice(),
                        part.getStockQuantity(),
                        part.getReservedStock(),
                        part.getMinimumStock(),
                        part.getCreatedAt(),
                        part.getUpdatedAt()
                ))
                .toList());
    }
}