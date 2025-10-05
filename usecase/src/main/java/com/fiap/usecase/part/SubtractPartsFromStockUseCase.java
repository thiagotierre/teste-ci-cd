package com.fiap.usecase.part;

import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import java.util.UUID;

public interface SubtractPartsFromStockUseCase {
    void execute(UUID partId, int quantity) throws NotFoundException, BusinessRuleException;
}