package com.fiap.challenge.workOrders.mapper;

import com.fiap.challenge.customers.mapper.CustomerMapper;
import com.fiap.challenge.vehicles.mapper.VehicleMapper;
import com.fiap.challenge.workOrders.dto.WorkOrderResumeDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkOrderMapper {

    private final CustomerMapper customerMapper;
    private final VehicleMapper vehicleMapper;
    private final WorkOrderPartMapper workOrderPartMapper;
    private final WorkOrderServiceMapper workOrderServiceMapper;

    public WorkOrderResumeDTO toWorkOrderResumeDTO(WorkOrderModel workOrderModel) {
        return new WorkOrderResumeDTO(
                workOrderModel.getId(),
                customerMapper.toCustomerResumeDTO(workOrderModel.getCustomer()),
                vehicleMapper.toVehicleResumeDTO(workOrderModel.getVehicle()),
                workOrderModel.getCreatedBy().getName(),
                workOrderModel.getAssignedMechanic() != null ? workOrderModel.getAssignedMechanic().getName() : null,
                workOrderPartMapper.toWorkOrderPartsDTO(workOrderModel.getWorkOrderPartModels()),
                workOrderServiceMapper.toWorkOrderServicesDTO(workOrderModel.getWorkOrderServices()),
                workOrderModel.getTotalAmount(),
                workOrderModel.getStatus()
        );
    }
}
