package com.fiap.challenge.workOrders.useCases.update;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import com.fiap.challenge.shared.exception.workOrder.WorkOrderNotAvailableException;
import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.usecases.find.FindUserByIdUseCase;
import com.fiap.challenge.workOrders.dto.AssignedMechanicResponseDTO;
import com.fiap.challenge.workOrders.dto.InputAssignMechanicDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.useCases.updateStatus.UpdateWorkOrderHistoryUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrderByIdUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AssignedMechanicUseCaseImplTest {

    @Mock
    private FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;
    @Mock
    private UpdateWorkOrderUseCase updateWorkOrderUseCase;
    @Mock
    private FindUserByIdUseCase findUserByIdUseCase;
    @Mock
    private UpdateWorkOrderHistoryUseCase updateWorkOrderHistoryUseCase;

    @InjectMocks
    private AssignedMechanicUseCaseImpl assignedMechanicUseCase;

    private UUID workOrderId;
    private UUID mechanicId;

    @BeforeEach
    void setup() {
        workOrderId = UUID.randomUUID();
        mechanicId = UUID.randomUUID();
    }

    @Test
    void deveAtribuirMecanicoComSucessoQuandoStatusForReceived() {
        // Arrange
        WorkOrderModel workOrder = new WorkOrderModel();
        workOrder.setId(workOrderId);
        workOrder.setStatus(WorkOrderStatus.RECEIVED);

        UserModel mechanic = new UserModel();
        mechanic.setId(mechanicId);

        when(findWorkOrderByIdUseCase.execute(workOrderId)).thenReturn(workOrder);
        when(findUserByIdUseCase.execute(mechanicId)).thenReturn(mechanic);

        InputAssignMechanicDTO dto = new InputAssignMechanicDTO(mechanicId);

        // Act
        ResponseApi<AssignedMechanicResponseDTO> response =
                assignedMechanicUseCase.execute(workOrderId, dto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus().value());
        assertEquals("Mecânico atribuído com sucesso!", response.getMessage());
        assertTrue(response.getData().success());

        verify(updateWorkOrderUseCase).execute(workOrder);
        verify(updateWorkOrderHistoryUseCase).execute(any());
        assertEquals(WorkOrderStatus.IN_DIAGNOSIS, workOrder.getStatus());
        assertEquals(mechanic, workOrder.getAssignedMechanic());
    }

    @Test
    void deveLancarExcecaoQuandoStatusNaoForReceived() {
        // Arrange
        WorkOrderModel workOrder = new WorkOrderModel();
        workOrder.setId(workOrderId);
        workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);

        when(findWorkOrderByIdUseCase.execute(workOrderId)).thenReturn(workOrder);

        InputAssignMechanicDTO dto = new InputAssignMechanicDTO(mechanicId);

        // Act & Assert
        assertThrows(WorkOrderNotAvailableException.class,
                () -> assignedMechanicUseCase.execute(workOrderId, dto));

        verify(updateWorkOrderUseCase, never()).execute(any());
        verify(updateWorkOrderHistoryUseCase, never()).execute(any());
    }
}