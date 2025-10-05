package com.fiap.usecase.part;

import com.fiap.core.exception.BusinessRuleException; // Importe a exceção
import com.fiap.core.exception.NotFoundException;
import java.util.UUID;

public interface DeletePartUseCase {
    void execute(UUID id) throws NotFoundException, BusinessRuleException;
}