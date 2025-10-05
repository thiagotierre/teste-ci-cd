package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.entity.WorkOrderAvarageTime;
import com.fiap.challenge.workOrders.useCases.create.CreateAvarageTimeWorkOrderUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindAvarageTimeWorkOrderUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvarageTimeWorkOrderUseCaseImplTest {

    @Mock
    private CreateAvarageTimeWorkOrderUseCase createAvarageTimeWorkOrderUseCase;

    @Mock
    private FindAvarageTimeWorkOrderUseCase findAvarageTimeWorkOrderUseCase;

    @InjectMocks
    private AvarageTimeWorkOrderUseCaseImpl useCase;

    private UUID workOrderId;

    @BeforeEach
    void setUp() {
        workOrderId = UUID.randomUUID();
    }

    @Test
    void executeInit_shouldCreateNewWorkOrderAvarageTime() {
        // Act
        useCase.executeInit(workOrderId);

        // Assert
        ArgumentCaptor<WorkOrderAvarageTime> captor = ArgumentCaptor.forClass(WorkOrderAvarageTime.class);
        verify(createAvarageTimeWorkOrderUseCase, times(1)).execute(captor.capture());

        WorkOrderAvarageTime captured = captor.getValue();
        assertEquals(workOrderId, captured.getWorkOrderId());
        assertNotNull(captured.getStartTime());
        assertNull(captured.getEndTime()); // Assuming initTime sets only startTime
    }

    @Test
    void executeEnd_shouldFinishAndUpdateExistingWorkOrderAvarageTime() {
        // Arrange
        WorkOrderAvarageTime existing = mock(WorkOrderAvarageTime.class);
        when(findAvarageTimeWorkOrderUseCase.execute(workOrderId)).thenReturn(existing);

        // Act
        useCase.executeEnd(workOrderId);

        // Assert
        verify(existing, times(1)).finishTime();
        verify(createAvarageTimeWorkOrderUseCase, times(1)).execute(existing);
    }

    @Test
    void executeEnd_shouldThrowExceptionWhenWorkOrderAvarageTimeNotFound() {
        // Arrange
        when(findAvarageTimeWorkOrderUseCase.execute(workOrderId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            useCase.executeEnd(workOrderId);
        });

        assertTrue(ex.getMessage().contains("Avarage time work order not found"));
        verify(createAvarageTimeWorkOrderUseCase, never()).execute(any());
    }
}