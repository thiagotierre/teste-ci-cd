package com.fiap.challenge.workOrders.history.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.history.WorkOrderHistoryModel;

public interface WorkOrderHistoryRepository extends JpaRepository<WorkOrderHistoryModel, UUID>{

	public List<WorkOrderHistoryModel> findByWorkOrderOrderByCreatedAtAsc(WorkOrderModel workOrder);
}
