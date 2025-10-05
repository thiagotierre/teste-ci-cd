package com.fiap.mapper.service;

import com.fiap.core.domain.service.Service;
import com.fiap.dto.service.CreateServiceRequest;
import com.fiap.dto.service.ServiceResponse;
import com.fiap.dto.service.UpdateServiceRequest;
import com.fiap.persistence.entity.service.ServiceEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ServiceMapper {

    public Service toDomain(CreateServiceRequest request) {
        return Service.builder()
                .name(request.name())
                .description(request.description())
                .basePrice(request.basePrice())
                .estimatedTimeMin(request.estimatedTimeMin())
                .build();
    }

    public Service toDomain(UUID id, UpdateServiceRequest request) {
        return Service.builder()
                .id(id)
                .name(request.name())
                .description(request.description())
                .basePrice(request.basePrice())
                .estimatedTimeMin(request.estimatedTimeMin())
                .build();
    }

    public ServiceResponse toResponse(Service service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getBasePrice(),
                service.getEstimatedTimeMin(),
                service.getCreatedAt(),
                service.getUpdatedAt()
        );
    }

    public ServiceEntity toEntity(Service service) {
        return ServiceEntity.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .basePrice(service.getBasePrice())
                .estimatedTimeMin(service.getEstimatedTimeMin())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }

    public Service toDomain(ServiceEntity entity) {
        return Service.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .basePrice(entity.getBasePrice())
                .estimatedTimeMin(entity.getEstimatedTimeMin())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}