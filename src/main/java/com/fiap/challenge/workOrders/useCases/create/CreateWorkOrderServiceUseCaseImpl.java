package com.fiap.challenge.workOrders.useCases.create;

import com.fiap.challenge.services.useCases.find.FindServiceModelByIdUseCase;
import com.fiap.challenge.workOrders.dto.WorkOrderServiceDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.WorkOrderServiceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateWorkOrderServiceUseCaseImpl implements CreateWorkOrderServiceUseCase {

    private final FindServiceModelByIdUseCase findServiceModelByIdUseCase;

    @Override
    public void execute(WorkOrderModel workOrderModel, List<WorkOrderServiceDTO> workOrderServiceDTOS) {

        List<WorkOrderServiceModel> workOrderServiceList = new ArrayList<>();
        var worOrderServicesModel = workOrderModel.getWorkOrderServices();

        for (WorkOrderServiceDTO service : workOrderServiceDTOS) {
            var workOrderServiceModel = new WorkOrderServiceModel();
            var serviceModel = findServiceModelByIdUseCase.execute(service.serviceId());

            workOrderServiceModel.setWorkOrder(workOrderModel);
            workOrderServiceModel.setServiceModel(serviceModel);
            workOrderServiceModel.setQuantity(service.quantity());
            workOrderServiceModel.setAppliedPrice(serviceModel.getBasePrice());

            workOrderServiceList.add(workOrderServiceModel);
        }

        if (worOrderServicesModel == null) worOrderServicesModel = new ArrayList<>();

        worOrderServicesModel.addAll(workOrderServiceList);
        workOrderModel.setWorkOrderServices(worOrderServicesModel);
    }
}
