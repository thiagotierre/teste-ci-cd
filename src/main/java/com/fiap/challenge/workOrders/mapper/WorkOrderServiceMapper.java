package com.fiap.challenge.workOrders.mapper;

import com.fiap.challenge.workOrders.dto.WorkOrderServiceDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderServiceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkOrderServiceMapper {

    public WorkOrderServiceDTO toWorkOrderServiceDTO(WorkOrderServiceModel model) {
        return new WorkOrderServiceDTO(
                model.getId(),
                model.getServiceModel().getName(),
                model.getQuantity()
        );
    }

    public List<WorkOrderServiceDTO> toWorkOrderServicesDTO(List<WorkOrderServiceModel> models) {
        return models.stream()
                .map(this::toWorkOrderServiceDTO).toList();
    }
}
