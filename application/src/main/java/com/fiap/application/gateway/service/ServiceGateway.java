package com.fiap.application.gateway.service;

import com.fiap.core.domain.service.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceGateway {
    Service create(Service service);
    Service update(Service service);
    void delete(UUID id);
    Optional<Service> findById(UUID id);
    List<Service> findByIds(List<UUID> ids);
    boolean existsById(UUID id);
}