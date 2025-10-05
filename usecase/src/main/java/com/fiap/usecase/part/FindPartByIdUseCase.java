package com.fiap.usecase.part;

import com.fiap.core.domain.part.Part;
import com.fiap.core.exception.NotFoundException;
import java.util.UUID;

public interface FindPartByIdUseCase {
    Part execute(UUID id) throws NotFoundException;
}