package com.fiap.challenge.workOrders.useCases.update;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.workOrders.dto.WorkOrderItemDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResumeDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.mapper.WorkOrderMapper;
import com.fiap.challenge.workOrders.useCases.create.CreateWorkOrderPartUseCase;
import com.fiap.challenge.workOrders.useCases.create.CreateWorkOrderServiceUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrderByIdUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateWorkOrderItemsUseCaseImplTest {

    @Mock
    private FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;

    @Mock
    private UpdateWorkOrderUseCase updateWorkOrderUseCase;

    @Mock
    private CreateWorkOrderPartUseCase createWorkOrderPartUseCase;

    @Mock
    private CreateWorkOrderServiceUseCase createWorkOrderServiceUseCase;

    @Mock
    private UpdateStatusWorkOrderUseCase updateStatusWorkOrderUseCase;

    @Mock
    private WorkOrderMapper workOrderMapper;

    @InjectMocks
    private UpdateWorkOrderItemsUseCaseImpl useCase;

    private UUID workOrderId;
    private WorkOrderModel workOrderModel;
    private WorkOrderItemDTO workOrderItemDTO;
    private WorkOrderResumeDTO expectedResumeDTO;

    @BeforeEach
    void setup() {
        workOrderId = UUID.randomUUID();
        workOrderModel = mock(WorkOrderModel.class);
        workOrderItemDTO = mock(WorkOrderItemDTO.class);
        expectedResumeDTO = mock(WorkOrderResumeDTO.class);

        when(findWorkOrderByIdUseCase.execute(workOrderId)).thenReturn(workOrderModel);
        when(workOrderItemDTO.parts()).thenReturn(List.of());
        when(workOrderItemDTO.services()).thenReturn(List.of());
        when(workOrderMapper.toWorkOrderResumeDTO(workOrderModel)).thenReturn(expectedResumeDTO);
        when(workOrderModel.getId()).thenReturn(workOrderId);
    }

    @Test
    void shouldUpdateWorkOrderItemsSuccessfully() {
        // Act
        WorkOrderResumeDTO result = useCase.execute(workOrderId, workOrderItemDTO).getData();

        // Assert
        verify(findWorkOrderByIdUseCase).execute(workOrderId);
        verify(createWorkOrderPartUseCase).execute(workOrderModel, workOrderItemDTO.parts());
        verify(createWorkOrderServiceUseCase).execute(workOrderModel, workOrderItemDTO.services());
        verify(workOrderModel).recalculateTotal();
        verify(updateStatusWorkOrderUseCase).execute(workOrderId, WorkOrderStatus.AWAITING_APPROVAL.name());
        verify(updateWorkOrderUseCase).execute(workOrderModel);
        verify(workOrderMapper).toWorkOrderResumeDTO(workOrderModel);

        assertEquals(expectedResumeDTO, result);
    }
}
