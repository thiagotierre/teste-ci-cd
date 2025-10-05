package com.fiap.challenge.workOrders.repository;

import com.fiap.challenge.workOrders.entity.WorkOrderAvarageTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AvarageTimeWorkOrderRepository extends JpaRepository<WorkOrderAvarageTime, UUID> {
    Optional<WorkOrderAvarageTime> findByWorkOrderId(UUID workOrderId);
}
