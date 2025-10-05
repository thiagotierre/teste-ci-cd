package com.fiap.challenge.parts.useCases.delete;

import com.fiap.challenge.shared.model.ResponseApi;

import java.util.UUID;

public interface DeletePartUseCase {
    ResponseApi<Void> execute(UUID id);
}