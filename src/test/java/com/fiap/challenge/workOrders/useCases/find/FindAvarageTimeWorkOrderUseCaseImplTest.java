package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.entity.WorkOrderAvarageTime;
import com.fiap.challenge.workOrders.repository.AvarageTimeWorkOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAvarageTimeWorkOrderUseCaseImplTest {

    @Mock
    private AvarageTimeWorkOrderRepository avarageTimeWorkOrderRepository;

    @InjectMocks
    private FindAvarageTimeWorkOrderUseCaseImpl useCase;

    private UUID workOrderId;

    @BeforeEach
    void setUp() {
        workOrderId = UUID.randomUUID();
    }

    @Test
    void shouldReturnWorkOrderAvarageTimeWhenFound() {
        // Arrange
        WorkOrderAvarageTime expected = new WorkOrderAvarageTime();
        when(avarageTimeWorkOrderRepository.findByWorkOrderId(workOrderId))
                .thenReturn(Optional.of(expected));

        // Act
        WorkOrderAvarageTime result = useCase.execute(workOrderId);

        // Assert
        assertEquals(expected, result);
        verify(avarageTimeWorkOrderRepository, times(1)).findByWorkOrderId(workOrderId);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenNotFound() {
        // Arrange
        when(avarageTimeWorkOrderRepository.findByWorkOrderId(workOrderId))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            useCase.execute(workOrderId);
        });

        assertTrue(ex.getMessage().contains(workOrderId.toString()));
        verify(avarageTimeWorkOrderRepository, times(1)).findByWorkOrderId(workOrderId);
    }

    @Test
    void shouldReturnNotFoundWhenListIsEmpty() {
        // Arrange
        when(avarageTimeWorkOrderRepository.findAll()).thenReturn(List.of());

        // Act
        ResponseApi<List<WorkOrderAvarageTime>> response = useCase.executeList();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals("No average time work orders found.", response.getMessage());
        assertNull(response.getData());
        verify(avarageTimeWorkOrderRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnOkWithListWhenDataExists() {
        // Arrange
        List<WorkOrderAvarageTime> list = List.of(new WorkOrderAvarageTime());
        when(avarageTimeWorkOrderRepository.findAll()).thenReturn(list);

        // Act
        ResponseApi<List<WorkOrderAvarageTime>> response = useCase.executeList();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Average time work orders retrieved successfully.", response.getMessage());
        assertEquals(list, response.getData());
        verify(avarageTimeWorkOrderRepository, times(1)).findAll();
    }
}