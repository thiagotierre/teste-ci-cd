package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.mapper.WorkOrderPartMapper;
import com.fiap.challenge.workOrders.mapper.WorkOrderServiceMapper;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindWorkOrderByIdUseCaseImplTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private WorkOrderPartMapper workOrderPartMapper;

    @Mock
    private WorkOrderServiceMapper workOrderServiceMapper;

    private FindWorkOrderByIdUseCaseImpl findWorkOrderByIdUseCase;

    private UUID workOrderId;

    @BeforeEach
    public void setup() {
        findWorkOrderByIdUseCase = new FindWorkOrderByIdUseCaseImpl(workOrderRepository, workOrderPartMapper, workOrderServiceMapper);
        workOrderId = UUID.randomUUID();
    }

    @Test
    void shouldReturnWorkOrderModelWhenFound() {
        WorkOrderModel workOrderModel = mock(WorkOrderModel.class);

        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(workOrderModel));

        var result = findWorkOrderByIdUseCase.execute(workOrderId);

        assertEquals(workOrderModel, result);
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> findWorkOrderByIdUseCase.execute(workOrderId));
    }

    @Test
    void shouldReturnDTOOnExecuteToDTO() {
        // Preparando o WorkOrderModel e entidades relacionadas
        WorkOrderModel workOrderModel = mock(WorkOrderModel.class);
        CustomerModel customer = mock(CustomerModel.class);
        VehicleModel vehicle = mock(VehicleModel.class);
        UserModel createdBy = mock(UserModel.class);

        // IDs para retornar nos mocks
        UUID customerId = UUID.randomUUID();
        UUID vehicleId = UUID.randomUUID();
        UUID createdById = UUID.randomUUID();

        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(workOrderModel));

        when(workOrderModel.getWorkOrderPartModels()).thenReturn(List.of());
        when(workOrderModel.getWorkOrderServices()).thenReturn(List.of());

        // Configurando os retornos dos relacionamentos e IDs
        when(workOrderModel.getCustomer()).thenReturn(customer);
        when(customer.getId()).thenReturn(customerId);

        when(workOrderModel.getVehicle()).thenReturn(vehicle);
        when(vehicle.getId()).thenReturn(vehicleId);

        when(workOrderModel.getCreatedBy()).thenReturn(createdBy);
        when(createdBy.getId()).thenReturn(createdById);

        when(workOrderModel.getAssignedMechanic()).thenReturn(null);
        when(workOrderModel.getTotalAmount()).thenReturn(null);
        when(workOrderModel.getId()).thenReturn(workOrderId);

        var dto = findWorkOrderByIdUseCase.executeToDTO(workOrderId).getData();

        assertNotNull(dto);
        assertEquals(workOrderId, dto.id());
        assertEquals(customerId, dto.customerId());
        assertEquals(vehicleId, dto.vehicleId());
        assertEquals(createdById, dto.createdById());
        assertNull(dto.assignedMechanicId());
        assertNull(dto.totalAmount());
        assertTrue(dto.parts().isEmpty());
        assertTrue(dto.services().isEmpty());
    }
}
