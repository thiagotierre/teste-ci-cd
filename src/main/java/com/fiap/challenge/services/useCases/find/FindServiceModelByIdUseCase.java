package com.fiap.challenge.services.useCases.find;

import com.fiap.challenge.services.entity.ServiceModel;

import java.util.UUID;

public interface FindServiceModelByIdUseCase {

	public ServiceModel execute(UUID id);
}
