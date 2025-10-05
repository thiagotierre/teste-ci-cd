package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindWorkOrderStatusByIdUseCaseImplTest {

    @Mock
    private FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;

    @InjectMocks
    private FindWorkOrderStatusByIdUseCaseImpl useCase;

    @Test
    void shouldReturnStatusWhenWorkOrderExists() {
        UUID id = UUID.randomUUID();
        WorkOrderModel workOrderModel = mock(WorkOrderModel.class);
        when(findWorkOrderByIdUseCase.execute(id)).thenReturn(workOrderModel);
        when(workOrderModel.getStatus()).thenReturn(WorkOrderStatus.RECEIVED);

        Optional<String> result = useCase.execute(id);

        assertTrue(result.isPresent());
        assertEquals("RECEIVED", result.get());

        verify(findWorkOrderByIdUseCase).execute(id);
        verify(workOrderModel).getStatus();
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenWorkOrderIsNull() {
        UUID id = UUID.randomUUID();
        when(findWorkOrderByIdUseCase.execute(id)).thenReturn(null);

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            useCase.execute(id);
        });

        assertEquals("Work order not found", thrown.getMessage());
        verify(findWorkOrderByIdUseCase).execute(id);
    }

    @Test
    void shouldReturnEmptyOptionalWhenStatusIsNull() {
        UUID id = UUID.randomUUID();
        WorkOrderModel workOrderModel = mock(WorkOrderModel.class);
        when(findWorkOrderByIdUseCase.execute(id)).thenReturn(workOrderModel);
        when(workOrderModel.getStatus()).thenReturn(null);

        Optional<String> result = useCase.execute(id);

        assertFalse(result.isPresent());

        verify(findWorkOrderByIdUseCase).execute(id);
        verify(workOrderModel).getStatus();
    }
}
