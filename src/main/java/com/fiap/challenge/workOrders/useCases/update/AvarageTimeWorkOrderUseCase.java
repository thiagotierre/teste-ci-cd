package com.fiap.challenge.workOrders.useCases.update;

import java.util.UUID;

public interface AvarageTimeWorkOrderUseCase {
    void executeInit(UUID id);
    void executeEnd(UUID id);
}
