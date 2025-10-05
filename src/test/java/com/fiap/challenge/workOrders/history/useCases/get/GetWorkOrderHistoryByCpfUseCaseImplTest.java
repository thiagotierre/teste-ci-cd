package com.fiap.challenge.workOrders.history.useCases.get;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.WorkOrderHistoryModel;
import com.fiap.challenge.workOrders.history.dto.WorkOrderHistoryItemDTO;
import com.fiap.challenge.workOrders.history.dto.WorkOrderWithHistoryResponseDTO;
import com.fiap.challenge.workOrders.history.repository.WorkOrderHistoryRepository;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetWorkOrderHistoryByCpfUseCaseImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private WorkOrderHistoryRepository workOrderHistoryRepository;

    @InjectMocks
    private GetWorkOrderHistoryByCpfUseCaseImpl useCase;

    private final String cpfCnpj = "12345678900";
    private CustomerModel customer;
    private WorkOrderModel workOrder;
    private WorkOrderHistoryModel historyEntry;

    @BeforeEach
    void setUp() {

        customer = new CustomerModel();
        customer.setId(UUID.randomUUID());
        customer.setCpfCnpj(cpfCnpj);
        customer.setName("Test Customer");

        workOrder = new WorkOrderModel();
        workOrder.setId(UUID.randomUUID());
        workOrder.setCustomer(customer);
        workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
        workOrder.setTotalAmount(new BigDecimal("1000.00"));
        workOrder.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));

        historyEntry = new WorkOrderHistoryModel();
        historyEntry.setId(UUID.randomUUID());
        historyEntry.setWorkOrder(workOrder);
        historyEntry.setStatus(workOrder.getStatus());
        historyEntry.setNotes("Initial status");
        historyEntry.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        when(customerRepository.findByCpfCnpj(cpfCnpj)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            useCase.execute(cpfCnpj);
        });

        assertTrue(exception.getMessage().contains("Cliente não encontrado"));
        verify(customerRepository, times(1)).findByCpfCnpj(cpfCnpj);
        verifyNoInteractions(workOrderRepository, workOrderHistoryRepository);
    }

    @Test
    void shouldReturnEmptyListWhenNoWorkOrders() {
        when(customerRepository.findByCpfCnpj(cpfCnpj)).thenReturn(Optional.of(customer));
        when(workOrderRepository.findByCustomerOrderByCreatedAtDesc(customer)).thenReturn(Collections.emptyList());

        List<WorkOrderWithHistoryResponseDTO> result = useCase.execute(cpfCnpj).getData();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(customerRepository, times(1)).findByCpfCnpj(cpfCnpj);
        verify(workOrderRepository, times(1)).findByCustomerOrderByCreatedAtDesc(customer);
        verifyNoInteractions(workOrderHistoryRepository);
    }

    @Test
    void shouldReturnWorkOrdersWithHistory() {
        when(customerRepository.findByCpfCnpj(cpfCnpj)).thenReturn(Optional.of(customer));
        when(workOrderRepository.findByCustomerOrderByCreatedAtDesc(customer)).thenReturn(List.of(workOrder));
        when(workOrderHistoryRepository.findByWorkOrderOrderByCreatedAtAsc(workOrder)).thenReturn(List.of(historyEntry));

        List<WorkOrderWithHistoryResponseDTO> result = useCase.execute(cpfCnpj).getData();

        assertNotNull(result);
        assertEquals(1, result.size());

        WorkOrderWithHistoryResponseDTO dto = result.get(0);
        assertEquals(workOrder.getId(), dto.workOrderId());
        assertEquals(workOrder.getTotalAmount(), dto.totalAmount());

        assertNotNull(dto.history());
        assertEquals(1, dto.history().size());

        WorkOrderHistoryItemDTO historyDTO = dto.history().get(0);
        assertEquals(historyEntry.getStatus().getDescription(), historyDTO.status());
        assertEquals(historyEntry.getNotes(), historyDTO.notes());
        assertEquals(historyEntry.getCreatedAt(), historyDTO.createdAt());

        verify(customerRepository, times(1)).findByCpfCnpj(cpfCnpj);
        verify(workOrderRepository, times(1)).findByCustomerOrderByCreatedAtDesc(customer);
        verify(workOrderHistoryRepository, times(1)).findByWorkOrderOrderByCreatedAtAsc(workOrder);
    }
}
