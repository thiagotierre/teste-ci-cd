package com.fiap.challenge.vehicles.find.byPlate;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.shared.exception.vehicle.VehicleNotFoundException;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.vehicles.useCases.find.byPlate.FindVehicleByPlateUseCaseImpl;
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
public class FindVehicleByPlateUseCaseImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private FindVehicleByPlateUseCaseImpl useCase;

    private UUID vehicleId;
    private UUID customerId;
    private CustomerModel customer;
    private VehicleModel vehicle;
    private final String plate = "ABC-1234";

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
                .licensePlate(plate)
                .brand("Toyota")
                .model("Corolla")
                .year(2020)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();
    }

    @Test
    void shouldReturnVehicleWhenPlateExists() {
        // Arrange
        when(vehicleRepository.findByLicensePlateIgnoreCase(plate)).thenReturn(Optional.of(vehicle));

        // Act
        VehicleResponseDTO result = useCase.execute(plate).getData();

        // Assert
        assertNotNull(result);
        assertEquals(vehicleId, result.id());
        assertEquals("Toyota", result.brand());
        assertEquals("Corolla", result.model());
        assertEquals(plate, result.licensePlate());
        verify(vehicleRepository, times(1)).findByLicensePlateIgnoreCase(plate);
    }

    @Test
    void shouldThrowExceptionWhenPlateDoesNotExist() {
        // Arrange
        when(vehicleRepository.findByLicensePlateIgnoreCase(plate)).thenReturn(Optional.empty());

        // Act & Assert
        VehicleNotFoundException exception = assertThrows(VehicleNotFoundException.class, () -> useCase.execute(plate));
        assertTrue(exception.getMessage().contains(plate));
        verify(vehicleRepository, times(1)).findByLicensePlateIgnoreCase(plate);
    }
}
