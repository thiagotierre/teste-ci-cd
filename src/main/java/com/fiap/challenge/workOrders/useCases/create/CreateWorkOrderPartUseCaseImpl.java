package com.fiap.challenge.workOrders.useCases.create;

import com.fiap.challenge.parts.useCases.find.FindPartModelByIdUseCase;
import com.fiap.challenge.workOrders.dto.WorkOrderPartDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.WorkOrderPartModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateWorkOrderPartUseCaseImpl implements CreateWorkOrderPartUseCase {

    private final FindPartModelByIdUseCase findPartModelByIdUseCase;

    @Override
    public void execute(WorkOrderModel workOrderModel, List<WorkOrderPartDTO> workOrderPartDTOS) {

        List<WorkOrderPartModel> workOrderPartList = new ArrayList<>();
        var workOrderPartsModel = workOrderModel.getWorkOrderPartModels();

        for (var wokOrderPart : workOrderPartDTOS) {
            var partModel = findPartModelByIdUseCase.execute(wokOrderPart.partId());
            var workOrderPart = new WorkOrderPartModel();

            workOrderPart.setWorkOrder(workOrderModel);
            workOrderPart.setPart(partModel);
            workOrderPart.setQuantity(wokOrderPart.quantity());
            workOrderPart.setUnitPrice(partModel.getPrice());

            workOrderPartList.add(workOrderPart);
        }

        if (workOrderPartsModel == null) workOrderPartsModel = new ArrayList<>();
        workOrderPartsModel.addAll(workOrderPartList);

        workOrderModel.setWorkOrderPartModels(workOrderPartsModel);
    }
}
