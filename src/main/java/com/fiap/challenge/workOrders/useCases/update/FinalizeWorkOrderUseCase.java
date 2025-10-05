package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.shared.model.ResponseApi;

import java.util.UUID;

public interface FinalizeWorkOrderUseCase {
    ResponseApi<Void> execute(UUID id);
}
