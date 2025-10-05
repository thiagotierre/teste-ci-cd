package com.fiap.challenge.workOrders.mapper;

import com.fiap.challenge.workOrders.dto.WorkOrderPartDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderPartModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkOrderPartMapper {

    public WorkOrderPartDTO toWorkOrderPartDTO(WorkOrderPartModel model) {
        return new WorkOrderPartDTO(
                model.getId(),
                model.getPart().getName(),
                model.getQuantity()
        );
    }

    public List<WorkOrderPartDTO> toWorkOrderPartsDTO(List<WorkOrderPartModel> models) {
        return models.stream()
                .map(this::toWorkOrderPartDTO).toList();
    }
}
