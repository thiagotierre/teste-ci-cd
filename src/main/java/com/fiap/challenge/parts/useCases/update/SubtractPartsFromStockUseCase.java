package com.fiap.challenge.parts.useCases.update;

import java.util.UUID;

public interface SubtractPartsFromStockUseCase {
    boolean execute(UUID partId, int quantity);
}
