package com.fiap.application.usecaseimpl.part;

import com.fiap.core.domain.part.Part;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.part.FindPartByIdUseCase;
import com.fiap.usecase.part.ReturnPartsToStockUseCase;
import com.fiap.usecase.part.UpdatePartUseCase;
import java.util.UUID;

public class ReturnPartsToStockUseCaseImpl implements ReturnPartsToStockUseCase {

    private final FindPartByIdUseCase findPartByIdUseCase;
    private final UpdatePartUseCase updatePartUseCase;

    public ReturnPartsToStockUseCaseImpl(FindPartByIdUseCase findPartByIdUseCase, UpdatePartUseCase updatePartUseCase) {
        this.findPartByIdUseCase = findPartByIdUseCase;
        this.updatePartUseCase = updatePartUseCase;
    }

    @Override
    public void execute(UUID partId, int quantity) throws NotFoundException, BusinessRuleException {
        Part part = findPartByIdUseCase.execute(partId);

        part.returnToStock(quantity);

        updatePartUseCase.execute(part);
    }
}