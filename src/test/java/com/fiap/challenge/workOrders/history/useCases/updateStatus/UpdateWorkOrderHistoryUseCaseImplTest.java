package com.fiap.challenge.workOrders.history.useCases.updateStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.WorkOrderHistoryModel;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderHistoryCommand;
import com.fiap.challenge.workOrders.history.repository.WorkOrderHistoryRepository;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UpdateWorkOrderHistoryUseCaseImplTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private WorkOrderHistoryRepository workOrderHistoryRepository;

    @InjectMocks
    private UpdateWorkOrderHistoryUseCaseImpl useCase;

    private UUID workOrderId;
    private WorkOrderModel workOrder;

    @BeforeEach
    void setUp() {
        workOrderId = UUID.randomUUID();

        workOrder = new WorkOrderModel();
        workOrder.setId(workOrderId);
        workOrder.setStatus(WorkOrderStatus.RECEIVED);
    }

    @Test
    void shouldCreateHistoryEntryWhenWorkOrderIsFound() {
        String notes = "Initial history entry.";
        var command = new UpdateWorkOrderHistoryCommand(workOrderId, notes);

        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(workOrder));

        WorkOrderModel result = useCase.execute(command);

        ArgumentCaptor<WorkOrderHistoryModel> historyCaptor = ArgumentCaptor.forClass(WorkOrderHistoryModel.class);

        verify(workOrderHistoryRepository, times(1)).save(historyCaptor.capture());

        WorkOrderHistoryModel savedHistory = historyCaptor.getValue();

        assertNotNull(savedHistory);
        assertEquals(workOrder, savedHistory.getWorkOrder());
        assertEquals(WorkOrderStatus.RECEIVED, savedHistory.getStatus());
        assertEquals(notes, savedHistory.getNotes());

        assertEquals(workOrder, result);

        verify(workOrderRepository, times(1)).findById(workOrderId);
    }

    @Test
    void shouldThrowExceptionWhenWorkOrderNotFound() {
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.empty());

        var command = new UpdateWorkOrderHistoryCommand(workOrderId, "some notes");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            useCase.execute(command);
        });

        assertTrue(exception.getMessage().contains("Work Order not found: " + workOrderId));
        verify(workOrderRepository, times(1)).findById(workOrderId);

        verifyNoInteractions(workOrderHistoryRepository);
    }
}