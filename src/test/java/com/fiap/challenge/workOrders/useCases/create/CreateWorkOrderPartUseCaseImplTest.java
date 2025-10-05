package com.fiap.challenge.workOrders.useCases.create;

import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.useCases.find.FindPartModelByIdUseCase;
import com.fiap.challenge.workOrders.dto.WorkOrderPartDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.WorkOrderPartModel;
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
public class CreateWorkOrderPartUseCaseImplTest {

    @Mock
    private FindPartModelByIdUseCase findPartModelByIdUseCase;

    @InjectMocks
    private CreateWorkOrderPartUseCaseImpl createWorkOrderPartUseCase;

    @Test
    void shouldAddWorkOrderPartsToWorkOrderModel() {
        // Arrange
        WorkOrderModel workOrderModel = new WorkOrderModel();
        workOrderModel.setWorkOrderPartModels(new ArrayList<>()); // já com lista vazia

        UUID partId1 = UUID.randomUUID();
        UUID partId2 = UUID.randomUUID();

        WorkOrderPartDTO dto1 = new WorkOrderPartDTO(partId1, "Parafuso", 2);
        WorkOrderPartDTO dto2 = new WorkOrderPartDTO(partId2, "Porca", 5);

        var part1 = new PartModel();
        part1.setId(partId1);
        part1.setPrice(new BigDecimal("10.00"));

        var part2 = new PartModel();
        part2.setId(partId2);
        part2.setPrice(new BigDecimal("20.00"));

        when(findPartModelByIdUseCase.execute(partId1)).thenReturn(part1);
        when(findPartModelByIdUseCase.execute(partId2)).thenReturn(part2);

        List<WorkOrderPartDTO> dtos = List.of(dto1, dto2);

        // Act
        createWorkOrderPartUseCase.execute(workOrderModel, dtos);

        // Assert
        List<WorkOrderPartModel> partsAdded = workOrderModel.getWorkOrderPartModels();

        assertNotNull(partsAdded);
        assertEquals(2, partsAdded.size());

        WorkOrderPartModel firstPart = partsAdded.get(0);
        assertEquals(workOrderModel, firstPart.getWorkOrder());
        assertEquals(part1, firstPart.getPart());
        assertEquals(2, firstPart.getQuantity());
        assertEquals(part1.getPrice(), firstPart.getUnitPrice());

        WorkOrderPartModel secondPart = partsAdded.get(1);
        assertEquals(workOrderModel, secondPart.getWorkOrder());
        assertEquals(part2, secondPart.getPart());
        assertEquals(5, secondPart.getQuantity());
        assertEquals(part2.getPrice(), secondPart.getUnitPrice());

        verify(findPartModelByIdUseCase, times(1)).execute(partId1);
        verify(findPartModelByIdUseCase, times(1)).execute(partId2);
    }

    @Test
    void shouldCreateWorkOrderPartListWhenInitialListIsNull() {
        // Arrange
        WorkOrderModel workOrderModel = new WorkOrderModel();
        workOrderModel.setWorkOrderPartModels(null); // lista inicial nula

        UUID partId = UUID.randomUUID();

        WorkOrderPartDTO dto = new WorkOrderPartDTO(partId, "Parafuso", 1);

        var part = new PartModel();
        part.setId(partId);
        part.setPrice(new BigDecimal("15.00"));

        when(findPartModelByIdUseCase.execute(partId)).thenReturn(part);

        // Act
        createWorkOrderPartUseCase.execute(workOrderModel, List.of(dto));

        // Assert
        List<WorkOrderPartModel> partsAdded = workOrderModel.getWorkOrderPartModels();

        assertNotNull(partsAdded);
        assertEquals(1, partsAdded.size());
        assertEquals(part, partsAdded.get(0).getPart());
    }
}
