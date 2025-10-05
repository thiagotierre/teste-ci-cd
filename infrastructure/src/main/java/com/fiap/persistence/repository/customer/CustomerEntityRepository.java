package com.fiap.persistence.repository.customer;

import com.fiap.persistence.entity.customer.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, UUID> {

    Boolean existsByDocumentNumber(String taxNumber);

    Boolean existsByEmail(String email);

    Optional<CustomerEntity> findByDocumentNumber(String documentNumber);
}
