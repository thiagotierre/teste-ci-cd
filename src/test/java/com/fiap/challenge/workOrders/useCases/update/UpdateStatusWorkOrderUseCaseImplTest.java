package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderHistoryCommand;
import com.fiap.challenge.workOrders.history.useCases.updateStatus.UpdateWorkOrderHistoryUseCase;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateStatusWorkOrderUseCaseImplTest {

    @Mock
    private WorkOrderRepository workOrderRepository;
    @Mock
    private UpdateWorkOrderHistoryUseCase updateWorkOrderHistoryUseCase;

    @InjectMocks
    private UpdateStatusWorkOrderUseCaseImpl updateStatusWorkOrderUseCase;

    private UUID workOrderId;
    private WorkOrderModel workOrderModel;

    @BeforeEach
    void setUp() {
        workOrderId = UUID.randomUUID();
        workOrderModel = new WorkOrderModel();
        workOrderModel.setId(workOrderId);
        workOrderModel.setStatus(WorkOrderStatus.RECEIVED);
    }

    @Test
    void deveLancarExcecaoQuandoWorkOrderNaoForEncontrada() {
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> updateStatusWorkOrderUseCase.execute(workOrderId, "COMPLETED"));

        verify(workOrderRepository).findById(workOrderId);
        verifyNoMoreInteractions(workOrderRepository, updateWorkOrderHistoryUseCase);
    }

    @Test
    void deveLancarExcecaoQuandoStatusForInvalido() {
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(workOrderModel));

        assertThrows(IllegalArgumentException.class,
                () -> updateStatusWorkOrderUseCase.execute(workOrderId, "INVALID_STATUS"));

        verify(workOrderRepository).findById(workOrderId);
        verifyNoMoreInteractions(workOrderRepository, updateWorkOrderHistoryUseCase);
    }

    @Test
    void deveRetornarMensagemQuandoStatusJaForIgualAoAtual() {
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(workOrderModel));

        ResponseApi<StatusWorkOrderRespondeDTO> response =
                updateStatusWorkOrderUseCase.execute(workOrderId, "RECEIVED");

        assertEquals(200, response.getStatus().value());
        assertTrue(response.getMessage().contains("No changes were made"));
        assertEquals(WorkOrderStatus.RECEIVED, response.getData().status());

        verify(workOrderRepository).findById(workOrderId);
        verifyNoMoreInteractions(workOrderRepository, updateWorkOrderHistoryUseCase);
    }

    @Test
    void deveAtualizarStatusComSucesso() {
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(workOrderModel));

        WorkOrderModel savedWorkOrder = new WorkOrderModel();
        savedWorkOrder.setId(workOrderId);
        savedWorkOrder.setStatus(WorkOrderStatus.COMPLETED);

        when(workOrderRepository.save(any(WorkOrderModel.class))).thenReturn(savedWorkOrder);

        ResponseApi<StatusWorkOrderRespondeDTO> response =
                updateStatusWorkOrderUseCase.execute(workOrderId, "COMPLETED");

        assertEquals(200, response.getStatus().value());
        assertEquals("Work order status updated successfully!", response.getMessage());
        assertEquals(WorkOrderStatus.COMPLETED, response.getData().status());

        verify(workOrderRepository).findById(workOrderId);
        verify(workOrderRepository).save(workOrderModel);
        verify(updateWorkOrderHistoryUseCase).execute(any(UpdateWorkOrderHistoryCommand.class));
    }
}