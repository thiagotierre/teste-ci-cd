package com.fiap.persistence.repository.part;

import com.fiap.persistence.entity.workOrder.WorkOrderPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface WorkOrderPartEntityRepository extends JpaRepository<WorkOrderPartEntity, UUID> {

    boolean existsByPartId(UUID partId);
}