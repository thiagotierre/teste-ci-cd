package com.fiap.challenge.workOrders.dto;

import com.fiap.challenge.customers.dto.CustomerResumeDTO;
import com.fiap.challenge.vehicles.dto.VehicleResumeDTO;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderResumeDTO {

    UUID workOrderId;
    CustomerResumeDTO customerResume;
    VehicleResumeDTO vehicleResume;
    String createdBy;
    String assignedMechanic;
    List<WorkOrderPartDTO> parts;
    List<WorkOrderServiceDTO> services;
    BigDecimal totalAmount;
    WorkOrderStatus status;
}
