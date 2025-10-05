package com.fiap.challenge.vehicles.find.byClientCpf;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.vehicles.useCases.find.byClientCpf.FindVehiclesByCustomerDocumentUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindVehiclesByCustomerDocumentUseCaseImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private FindVehiclesByCustomerDocumentUseCaseImpl useCase;

    private UUID customerId;
    private UUID vehicleId;
    private CustomerModel customer;
    private VehicleModel vehicle;
    private final String cpfCnpj = "12345678900";

    @BeforeEach
    void setUp() {

        customerId = UUID.randomUUID();
        vehicleId = UUID.randomUUID();

        customer = CustomerModel.builder()
                .id(customerId)
                .name("John Doe")
                .cpfCnpj(cpfCnpj)
                .build();

        vehicle = VehicleModel.builder()
                .id(vehicleId)
                .customer(customer)
                .licensePlate("ABC-1234")
                .brand("Toyota")
                .model("Corolla")
                .year(2020)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();
    }

    @Test
    void shouldReturnVehiclesWhenCustomerExists() {
        // Arrange
        when(customerRepository.existsByCpfCnpj(cpfCnpj)).thenReturn(true);
        when(vehicleRepository.findByCustomerCpfCnpj(cpfCnpj)).thenReturn(List.of(vehicle));

        // Act
        List<VehicleResponseDTO> result = useCase.execute(cpfCnpj).getData();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(vehicleId, result.get(0).id());
        assertEquals("Toyota", result.get(0).brand());
        verify(customerRepository, times(1)).existsByCpfCnpj(cpfCnpj);
        verify(vehicleRepository, times(1)).findByCustomerCpfCnpj(cpfCnpj);
    }

    @Test
    void shouldThrowExceptionWhenCustomerDoesNotExist() {
        // Arrange
        when(customerRepository.existsByCpfCnpj(cpfCnpj)).thenReturn(false);

        // Act & Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> useCase.execute(cpfCnpj));
        assertTrue(exception.getMessage().contains(cpfCnpj));
        verify(customerRepository, times(1)).existsByCpfCnpj(cpfCnpj);
        verify(vehicleRepository, never()).findByCustomerCpfCnpj(anyString());
    }
}
