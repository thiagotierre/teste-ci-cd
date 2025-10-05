package com.fiap.usecase.part;

import com.fiap.core.domain.part.Part;
import com.fiap.core.exception.NotFoundException;

public interface UpdatePartUseCase {
    Part execute(Part part) throws NotFoundException;
}