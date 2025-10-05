package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.shared.model.ResponseApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinalizeWorkOrderUseCaseimplTest {

    @Mock
    private UpdateStatusWorkOrderUseCase updateStatusWorkOrderUseCase;

    @Mock
    private AvarageTimeWorkOrderUseCase avarageTimeWorkOrderUseCase;

    @InjectMocks
    private FinalizeWorkOrderUseCaseimpl useCase;

    private UUID workOrderId;

    @BeforeEach
    void setUp() {
        workOrderId = UUID.randomUUID();
    }

    @Test
    void execute_shouldCallUpdateStatusAndExecuteEndAndReturnOk() {
        // Arrange
        when(updateStatusWorkOrderUseCase.execute(any(UUID.class), anyString()))
                .thenReturn(new ResponseApi<>());

        // Act
        ResponseApi<Void> response = useCase.execute(workOrderId);

        // Assert
        verify(updateStatusWorkOrderUseCase, times(1)).execute(workOrderId, "COMPLETED");
        verify(avarageTimeWorkOrderUseCase, times(1)).executeEnd(workOrderId);

        assertNotNull(response);
        assertEquals(200, response.getStatus().value());
        assertEquals("Ordem de serviço finalizada com sucesso!", response.getMessage());
    }
}