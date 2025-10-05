package com.fiap.gateway.service;

import com.fiap.application.gateway.service.ServiceGateway;
import com.fiap.core.domain.service.Service;
import com.fiap.mapper.service.ServiceMapper;
import com.fiap.persistence.entity.service.ServiceEntity;
import com.fiap.persistence.repository.service.ServiceEntityRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ServiceRepositoryGateway implements ServiceGateway {

    private final ServiceEntityRepository repository;
    private final ServiceMapper mapper;

    public ServiceRepositoryGateway(ServiceEntityRepository repository, ServiceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Service create(Service service) {
        ServiceEntity entity = mapper.toEntity(service);
        ServiceEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Service update(Service service) {
        ServiceEntity entity = mapper.toEntity(service);
        ServiceEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Service> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Service> findByIds(List<UUID> ids) {
        return repository.findAllById(ids).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}