package com.fiap.challenge.workOrders.repository;

import com.fiap.challenge.workOrders.entity.WorkOrderPartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkOrderPartRepository extends JpaRepository<WorkOrderPartModel, UUID> {
    public boolean existsByPartId(UUID partId);
}
