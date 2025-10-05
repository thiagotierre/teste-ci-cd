package com.fiap.challenge.workOrders.useCases.create;

import com.fiap.challenge.services.useCases.find.FindServiceModelByIdUseCase;
import com.fiap.challenge.workOrders.dto.WorkOrderServiceDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.WorkOrderServiceModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateWorkOrderServiceUseCaseImplTest {

    @Mock
    private FindServiceModelByIdUseCase findServiceModelByIdUseCase;

    @InjectMocks
    private CreateWorkOrderServiceUseCaseImpl createWorkOrderServiceUseCase;

    @Test
    void shouldAddWorkOrderServicesToWorkOrderModel() {
        // Arrange
        WorkOrderModel workOrderModel = new WorkOrderModel();
        workOrderModel.setWorkOrderServices(new ArrayList<>());

        UUID serviceId1 = UUID.randomUUID();
        UUID serviceId2 = UUID.randomUUID();

        WorkOrderServiceDTO dto1 = new WorkOrderServiceDTO(serviceId1, "Service A", 3);
        WorkOrderServiceDTO dto2 = new WorkOrderServiceDTO(serviceId2, "Service B", 1);

        var serviceModel1 = new com.fiap.challenge.services.entity.ServiceModel();
        serviceModel1.setId(serviceId1);
        serviceModel1.setBasePrice(new BigDecimal("100.00"));

        var serviceModel2 = new com.fiap.challenge.services.entity.ServiceModel();
        serviceModel2.setId(serviceId2);
        serviceModel2.setBasePrice(new BigDecimal("200.00"));

        when(findServiceModelByIdUseCase.execute(serviceId1)).thenReturn(serviceModel1);
        when(findServiceModelByIdUseCase.execute(serviceId2)).thenReturn(serviceModel2);

        List<WorkOrderServiceDTO> dtos = List.of(dto1, dto2);

        // Act
        createWorkOrderServiceUseCase.execute(workOrderModel, dtos);

        // Assert
        List<WorkOrderServiceModel> servicesAdded = workOrderModel.getWorkOrderServices();

        assertNotNull(servicesAdded);
        assertEquals(2, servicesAdded.size());

        WorkOrderServiceModel firstService = servicesAdded.get(0);
        assertEquals(workOrderModel, firstService.getWorkOrder());
        assertEquals(serviceModel1, firstService.getServiceModel());
        assertEquals(3, firstService.getQuantity());
        assertEquals(serviceModel1.getBasePrice(), firstService.getAppliedPrice());

        WorkOrderServiceModel secondService = servicesAdded.get(1);
        assertEquals(workOrderModel, secondService.getWorkOrder());
        assertEquals(serviceModel2, secondService.getServiceModel());
        assertEquals(1, secondService.getQuantity());
        assertEquals(serviceModel2.getBasePrice(), secondService.getAppliedPrice());

        verify(findServiceModelByIdUseCase, times(1)).execute(serviceId1);
        verify(findServiceModelByIdUseCase, times(1)).execute(serviceId2);
    }

    @Test
    void shouldCreateWorkOrderServiceListWhenInitialListIsNull() {
        // Arrange
        WorkOrderModel workOrderModel = new WorkOrderModel();
        workOrderModel.setWorkOrderServices(null);

        UUID serviceId = UUID.randomUUID();

        WorkOrderServiceDTO dto = new WorkOrderServiceDTO(serviceId, "Service X", 1);

        var serviceModel = new com.fiap.challenge.services.entity.ServiceModel();
        serviceModel.setId(serviceId);
        serviceModel.setBasePrice(new BigDecimal("150.00"));

        when(findServiceModelByIdUseCase.execute(serviceId)).thenReturn(serviceModel);

        // Act
        createWorkOrderServiceUseCase.execute(workOrderModel, List.of(dto));

        // Assert
        List<WorkOrderServiceModel> servicesAdded = workOrderModel.getWorkOrderServices();

        assertNotNull(servicesAdded);
        assertEquals(1, servicesAdded.size());
        assertEquals(serviceModel, servicesAdded.get(0).getServiceModel());
    }
}
