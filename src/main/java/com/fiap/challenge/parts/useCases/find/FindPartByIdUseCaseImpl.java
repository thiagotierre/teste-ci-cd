package com.fiap.challenge.parts.useCases.find;

import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.repository.PartsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindPartByIdUseCaseImpl implements FindPartByIdUseCase {

    private final PartsRepository partRepository;

    @Override
    public ResponseApi<PartResponseDTO> execute(UUID id) {
        ResponseApi<PartResponseDTO> responseApi = new ResponseApi<>();
    partRepository
        .findById(id)
        .map(
            part ->
                new PartResponseDTO(
                    part.getId(),
                    part.getName(),
                    part.getDescription(),
                    part.getPrice(),
                    part.getStockQuantity(),
                    part.getReservedStock(),
                    part.getMinimumStock(),
                    part.getCreatedAt(),
                    part.getUpdatedAt()))
        .ifPresentOrElse(
            partResponseDTO ->
              responseApi.of(HttpStatus.OK, "Part found successfully.", partResponseDTO),
            () -> responseApi.of(HttpStatus.NOT_FOUND, "Part not found with ID: " + id, null));
                return responseApi;
    }
}