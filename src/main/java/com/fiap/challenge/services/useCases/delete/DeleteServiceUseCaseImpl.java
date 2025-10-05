package com.fiap.challenge.services.useCases.delete;

import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.shared.exception.serice.ServiceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteServiceUseCaseImpl implements DeleteServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public ResponseApi<Void> execute(UUID id) {
        ResponseApi<Void> responseApi = new ResponseApi<>();
        if (!this.serviceRepository.existsById(id)) {
            throw new ServiceNotFoundException(id);
        }

        this.serviceRepository.deleteById(id);
        return responseApi.of(HttpStatus.NO_CONTENT, "Serviço deletado com sucesso");
    }
}