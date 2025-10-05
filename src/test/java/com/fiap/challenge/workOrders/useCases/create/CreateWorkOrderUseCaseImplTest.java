package com.fiap.challenge.workOrders.useCases.create;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.parts.useCases.update.SubtractPartsFromStockUseCase;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.repository.UserRepository;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.workOrders.dto.WorkOrderDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderPartDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResponseDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderServiceDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.WorkOrderPartModel;
import com.fiap.challenge.workOrders.entity.WorkOrderServiceModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.useCases.updateStatus.UpdateWorkOrderHistoryUseCase;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CreateWorkOrderUseCaseImplTest {

    @InjectMocks
    private CreateWorkOrderUseCase createWorkOrderUseCase;

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreateWorkOrderPartUseCase createWorkOrderPartUseCase;

    @Mock
    private CreateWorkOrderServiceUseCase createWorkOrderServiceUseCase;

    @Mock
    private UpdateWorkOrderHistoryUseCase updateWorkOrderStatusUseCase;

    @Mock
    private SubtractPartsFromStockUseCase subtractPartsFromStockUseCase;

    private final UUID customerId = UUID.randomUUID();
    private final UUID vehicleId = UUID.randomUUID();
    private final UUID createdById = UUID.randomUUID();
    private final UUID mechanicId = UUID.randomUUID();

    private final UUID partId = UUID.randomUUID();
    private final UUID serviceId = UUID.randomUUID();

    @BeforeEach
    void setup() {
        // Mocks para entidades
        CustomerModel mockCustomer = new CustomerModel();
        VehicleModel mockVehicle = new VehicleModel();
        UserModel mockUser = new UserModel();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(mockVehicle));
        when(userRepository.findById(createdById)).thenReturn(Optional.of(mockUser));
        when(userRepository.findById(mechanicId)).thenReturn(Optional.of(mockUser));

        // Simula criação de partes: adiciona itens na lista do WorkOrderModel
        doAnswer(invocation -> {
            WorkOrderModel wo = invocation.getArgument(0);
            List<WorkOrderPartDTO> parts = invocation.getArgument(1);
            // cria lista simples com WorkOrderPartModel para simular adição
            List<WorkOrderPartModel> partsModels = parts.stream().map(p -> {
                var partModel = new WorkOrderPartModel();
                partModel.setWorkOrder(wo);
                partModel.setQuantity(p.quantity());
                partModel.setUnitPrice(BigDecimal.valueOf(100));
                return partModel;
            }).toList();
            wo.setWorkOrderPartModels(new ArrayList<>(partsModels));
            return null;
        }).when(createWorkOrderPartUseCase).execute(any(), anyList());

        // Simula criação de serviços: adiciona itens na lista do WorkOrderModel
        doAnswer(invocation -> {
            WorkOrderModel wo = invocation.getArgument(0);
            List<WorkOrderServiceDTO> services = invocation.getArgument(1);
            List<WorkOrderServiceModel> servicesModels = services.stream().map(s -> {
                var serviceModel = new WorkOrderServiceModel();
                serviceModel.setWorkOrder(wo);
                serviceModel.setQuantity(s.quantity());
                serviceModel.setAppliedPrice(BigDecimal.valueOf(100));
                return serviceModel;
            }).toList();
            wo.setWorkOrderServices(new ArrayList<>(servicesModels));
            return null;
        }).when(createWorkOrderServiceUseCase).execute(any(), anyList());

        // Simula estoque OK
        when(subtractPartsFromStockUseCase.execute(any(UUID.class), anyInt())).thenReturn(true);

        // Salva retorna o objeto passado
        when(workOrderRepository.save(any(WorkOrderModel.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void shouldCreateWorkOrderSuccessfully() {
        WorkOrderPartDTO partDTO = new WorkOrderPartDTO(partId, "Part", 1);
        WorkOrderServiceDTO serviceDTO = new WorkOrderServiceDTO(serviceId, "Service", 1);

        WorkOrderDTO dto = new WorkOrderDTO(
                customerId,
                vehicleId,
                createdById,
                mechanicId,
                List.of(partDTO),
                List.of(serviceDTO)
        );

        WorkOrderResponseDTO result = createWorkOrderUseCase.execute(dto).getData();

        assertNotNull(result);
        assertFalse(result.parts().isEmpty());
        assertFalse(result.services().isEmpty());
    }

    @Test
    void shouldThrowEntityNotFoundWhenCustomerMissing() {
        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.empty());

        WorkOrderDTO dto = new WorkOrderDTO(
                customerId,
                vehicleId,
                createdById,
                mechanicId,
                List.of(),
                List.of()
        );

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> createWorkOrderUseCase.execute(dto));
        assertEquals("Cliente não encontrado", ex.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundWhenVehicleMissing() {
        when(vehicleRepository.findById(vehicleId)).thenReturn(java.util.Optional.empty());

        WorkOrderDTO dto = new WorkOrderDTO(
                customerId,
                vehicleId,
                createdById,
                mechanicId,
                List.of(),
                List.of()
        );

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> createWorkOrderUseCase.execute(dto));
        assertEquals("Veículo não encontrado", ex.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundWhenCreatedByUserMissing() {
        when(userRepository.findById(createdById)).thenReturn(java.util.Optional.empty());

        WorkOrderDTO dto = new WorkOrderDTO(
                customerId,
                vehicleId,
                createdById,
                mechanicId,
                List.of(),
                List.of()
        );

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> createWorkOrderUseCase.execute(dto));
        assertEquals("Usuário criador não encontrado", ex.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundWhenAssignedMechanicMissing() {
        when(userRepository.findById(mechanicId)).thenReturn(java.util.Optional.empty());

        WorkOrderDTO dto = new WorkOrderDTO(
                customerId,
                vehicleId,
                createdById,
                mechanicId,
                List.of(new WorkOrderPartDTO(partId, "Part", 1)),
                List.of()
        );

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> createWorkOrderUseCase.execute(dto));
        assertEquals("Mecânico atribuído não encontrado", ex.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentWhenNoPartsAndNoServices() {
        WorkOrderDTO dto = new WorkOrderDTO(
                customerId,
                vehicleId,
                createdById,
                null,
                List.of(),
                List.of()
        );

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> createWorkOrderUseCase.execute(dto));
        assertEquals("Cada item deve ter ao menos uma peça ou serviço associado", ex.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentWhenStockInsufficient() {
        when(subtractPartsFromStockUseCase.execute(any(UUID.class), anyInt())).thenReturn(false);

        WorkOrderPartDTO partDTO = new WorkOrderPartDTO(partId, "Part", 5);

        WorkOrderDTO dto = new WorkOrderDTO(
                customerId,
                vehicleId,
                createdById,
                null,
                List.of(partDTO),
                List.of()
        );

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> createWorkOrderUseCase.execute(dto));
        assertEquals("Estoque insuficiente para a peça com ID: " + partId, ex.getMessage());
    }
}
