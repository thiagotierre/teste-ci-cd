package com.fiap.challenge.services.useCases.delete;

import com.fiap.challenge.shared.model.ResponseApi;

import java.util.UUID;

public interface DeleteServiceUseCase {

	ResponseApi<Void> execute(UUID id);
}
