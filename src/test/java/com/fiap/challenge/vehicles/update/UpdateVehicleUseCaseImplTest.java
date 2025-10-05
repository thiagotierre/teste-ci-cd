package com.fiap.challenge.vehicles.update;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;
import com.fiap.challenge.shared.exception.vehicle.VehicleNotFoundException;
import com.fiap.challenge.vehicles.dto.InputVehicleDTO;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.vehicles.useCases.update.UpdateVehicleUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateVehicleUseCaseImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private UpdateVehicleUseCaseImpl useCase;

    private UUID vehicleId;
    private UUID customerId;
    private InputVehicleDTO inputDTO;
    private VehicleModel existingVehicle;
    private CustomerModel customer;
    private VehicleModel updatedVehicle;

    @BeforeEach
    void setUp() {

        vehicleId = UUID.randomUUID();
        customerId = UUID.randomUUID();

        customer = CustomerModel.builder()
                .id(customerId)
                .name("John Doe")
                .build();

        existingVehicle = VehicleModel.builder()
                .id(vehicleId)
                .customer(customer)
                .licensePlate("OLD-1234")
                .brand("OldBrand")
                .model("OldModel")
                .year(2015)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        inputDTO = new InputVehicleDTO(
                customerId,
                "NEW-5678",
                "NewBrand",
                "NewModel",
                2022
        );

        updatedVehicle = VehicleModel.builder()
                .id(vehicleId)
                .customer(customer)
                .licensePlate("NEW-5678")
                .brand("NewBrand")
                .model("NewModel")
                .year(2022)
                .createdAt(existingVehicle.getCreatedAt())
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();
    }

    @Test
    void shouldUpdateVehicleSuccessfully() {
        // Arrange
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(existingVehicle));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(vehicleRepository.save(any(VehicleModel.class))).thenReturn(updatedVehicle);

        // Act
        VehicleResponseDTO response = useCase.execute(vehicleId, inputDTO).getData();

        // Assert
        assertNotNull(response);
        assertEquals(vehicleId, response.id());
        assertEquals("NewBrand", response.brand());
        assertEquals("NewModel", response.model());
        assertEquals(2022, response.year());
        assertEquals(customerId, response.customer().id());

        verify(vehicleRepository, times(1)).findById(vehicleId);
        verify(customerRepository, times(1)).findById(customerId);
        verify(vehicleRepository, times(1)).save(any(VehicleModel.class));
    }

    @Test
    void shouldThrowExceptionWhenVehicleNotFound() {
        // Arrange
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VehicleNotFoundException.class, () -> useCase.execute(vehicleId, inputDTO));

        verify(vehicleRepository, times(1)).findById(vehicleId);
        verify(customerRepository, never()).findById(any());
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(existingVehicle));
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> useCase.execute(vehicleId, inputDTO));

        verify(vehicleRepository, times(1)).findById(vehicleId);
        verify(customerRepository, times(1)).findById(customerId);
        verify(vehicleRepository, never()).save(any());
    }
}
