package com.fiap.persistence.repository.service;

import com.fiap.persistence.entity.service.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, UUID> {
}