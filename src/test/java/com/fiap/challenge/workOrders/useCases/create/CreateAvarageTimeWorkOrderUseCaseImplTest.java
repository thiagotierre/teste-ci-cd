package com.fiap.challenge.workOrders.useCases.create;

import com.fiap.challenge.workOrders.entity.WorkOrderAvarageTime;
import com.fiap.challenge.workOrders.repository.AvarageTimeWorkOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateAvarageTimeWorkOrderUseCaseImplTest {

    @Mock
    private AvarageTimeWorkOrderRepository avarageTimeWorkOrderRepository;

    @InjectMocks
    private CreateAvarageTimeWorkOrderUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldSaveWorkOrderAvarageTimeSuccessfully() {
        // Arrange
        WorkOrderAvarageTime workOrderAvarageTime = new WorkOrderAvarageTime();

        // Act
        useCase.execute(workOrderAvarageTime);

        // Assert
        ArgumentCaptor<WorkOrderAvarageTime> captor = ArgumentCaptor.forClass(WorkOrderAvarageTime.class);
        verify(avarageTimeWorkOrderRepository, times(1)).save(captor.capture());

        assertEquals(workOrderAvarageTime, captor.getValue());
    }

    @Test
    void shouldThrowExceptionIfRepositoryFails() {
        // Arrange
        WorkOrderAvarageTime workOrderAvarageTime = new WorkOrderAvarageTime();
        doThrow(new RuntimeException("Database error")).when(avarageTimeWorkOrderRepository).save(any());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            useCase.execute(workOrderAvarageTime);
        });

        assertEquals("Database error", ex.getMessage());
        verify(avarageTimeWorkOrderRepository, times(1)).save(workOrderAvarageTime);
    }
}