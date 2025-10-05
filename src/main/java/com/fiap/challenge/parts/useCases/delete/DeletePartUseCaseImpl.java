package com.fiap.challenge.parts.useCases.delete;

import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.repository.WorkOrderPartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.shared.exception.part.PartDeletionException;
import com.fiap.challenge.shared.exception.part.PartNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeletePartUseCaseImpl implements DeletePartUseCase {

    private final PartsRepository partsRepository;
    private final WorkOrderPartRepository workOrderPartRepository;

    @Override
    @Transactional
    public ResponseApi<Void> execute(UUID id) {
        ResponseApi<Void> responseApi = new ResponseApi<>();
        if (!partsRepository.existsById(id)) {
            throw new PartNotFoundException(id);
        }

        if (workOrderPartRepository.existsByPartId(id)) {
            throw new PartDeletionException(id);
        }

        partsRepository.deleteById(id);

        return responseApi.of(HttpStatus.NO_CONTENT,
                "Peça deletada com sucesso");
    }
}