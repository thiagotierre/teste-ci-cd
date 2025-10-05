package com.fiap.challenge.vehicles.find;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.shared.exception.vehicle.VehicleNotFoundException;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.vehicles.useCases.find.FindVehicleByIdUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindVehicleByIdUseCaseImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private FindVehicleByIdUseCaseImpl useCase;

    private UUID vehicleId;
    private UUID customerId;
    private VehicleModel vehicle;
    private CustomerModel customer;

    @BeforeEach
    void setUp() {

        vehicleId = UUID.randomUUID();
        customerId = UUID.randomUUID();

        customer = CustomerModel.builder()
                .id(customerId)
                .name("John Doe")
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
    void shouldReturnVehicleWhenFound() {
        // Arrange
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));

        // Act
        VehicleResponseDTO response = useCase.execute(vehicleId).getData();

        // Assert
        assertNotNull(response);
        assertEquals(vehicleId, response.id());
        assertEquals("Toyota", response.brand());
        assertEquals("Corolla", response.model());
        assertEquals(2020, response.year());
        assertEquals(customerId, response.customer().id());
        assertEquals("John Doe", response.customer().name());

        verify(vehicleRepository, times(1)).findById(vehicleId);
    }

    @Test
    void shouldThrowExceptionWhenVehicleNotFound() {
        // Arrange
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VehicleNotFoundException.class, () -> useCase.execute(vehicleId));

        verify(vehicleRepository, times(1)).findById(vehicleId);
    }
}
