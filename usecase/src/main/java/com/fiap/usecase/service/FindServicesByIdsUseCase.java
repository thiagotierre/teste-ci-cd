package com.fiap.usecase.service;

import com.fiap.core.domain.service.Service;
import java.util.List;
import java.util.UUID;

public interface FindServicesByIdsUseCase {
    List<Service> execute(List<UUID> ids);
}