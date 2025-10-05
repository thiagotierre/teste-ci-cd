package com.fiap.persistence.repository.part;

import com.fiap.persistence.entity.part.PartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PartEntityRepository extends JpaRepository<PartEntity, UUID> {
}