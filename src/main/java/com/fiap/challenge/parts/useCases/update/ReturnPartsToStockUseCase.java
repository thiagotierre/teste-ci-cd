package com.fiap.challenge.parts.useCases.update;

import java.util.UUID;

public interface ReturnPartsToStockUseCase {
    boolean execute(UUID partId, int quantity);
}
