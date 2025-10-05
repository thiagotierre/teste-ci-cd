package com.fiap.usecase.part;

import com.fiap.core.domain.part.Part;
import java.util.List;
import java.util.UUID;

public interface FindPartsByIdsUseCase {
    List<Part> execute(List<UUID> ids);
}