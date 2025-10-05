package com.fiap.challenge.vehicles.create;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;
import com.fiap.challenge.vehicles.dto.InputVehicleDTO;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.vehicles.useCases.create.CreateVehicleUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateVehicleUseCaseImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateVehicleUseCaseImpl useCase;

    private UUID customerId;
    private InputVehicleDTO inputDTO;
    private CustomerModel customer;
    private VehicleModel savedVehicle;

    @BeforeEach
    void setUp() {

        customerId = UUID.randomUUID();

        customer = CustomerModel.builder()
                .id(customerId)
                .name("John Doe")
                .build();

        inputDTO = new InputVehicleDTO(
                customerId,
                "ABC-1234",
                "Toyota",
                "Corolla",
                2020
        );

        savedVehicle = VehicleModel.builder()
                .id(UUID.randomUUID())
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
    void shouldCreateVehicleSuccessfully() {
        // Arrange
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(vehicleRepository.save(any(VehicleModel.class))).thenReturn(savedVehicle);

        // Act
        VehicleResponseDTO response = useCase.execute(inputDTO).getData();

        // Assert
        assertNotNull(response);
        assertEquals(savedVehicle.getId(), response.id());
        assertEquals("Toyota", response.brand());
        assertEquals("Corolla", response.model());
        assertEquals(2020, response.year());
        assertEquals(customerId, response.customer().id());

        verify(customerRepository, times(1)).findById(customerId);
        verify(vehicleRepository, times(1)).save(any(VehicleModel.class));
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> useCase.execute(inputDTO));

        verify(vehicleRepository, never()).save(any(VehicleModel.class));
    }
}
