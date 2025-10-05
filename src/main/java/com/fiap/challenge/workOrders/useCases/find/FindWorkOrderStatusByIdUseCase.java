package com.fiap.challenge.workOrders.useCases.find;

import java.util.Optional;
import java.util.UUID;

public interface FindWorkOrderStatusByIdUseCase {

    Optional<String> execute(UUID id);
}
