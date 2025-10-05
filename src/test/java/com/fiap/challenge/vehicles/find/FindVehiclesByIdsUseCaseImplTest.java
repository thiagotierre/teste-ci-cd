package com.fiap.challenge.vehicles.find;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.vehicles.useCases.find.FindVehiclesByIdsUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindVehiclesByIdsUseCaseImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private FindVehiclesByIdsUseCaseImpl useCase;

    private UUID vehicleId1;
    private UUID vehicleId2;
    private UUID customerId;
    private CustomerModel customer;
    private VehicleModel vehicle1;
    private VehicleModel vehicle2;

    @BeforeEach
    void setUp() {

        vehicleId1 = UUID.randomUUID();
        vehicleId2 = UUID.randomUUID();
        customerId = UUID.randomUUID();

        customer = CustomerModel.builder()
                .id(customerId)
                .name("John Doe")
                .build();

        vehicle1 = VehicleModel.builder()
                .id(vehicleId1)
                .customer(customer)
                .licensePlate("ABC-1234")
                .brand("Toyota")
                .model("Corolla")
                .year(2020)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        vehicle2 = VehicleModel.builder()
                .id(vehicleId2)
                .customer(customer)
                .licensePlate("XYZ-5678")
                .brand("Honda")
                .model("Civic")
                .year(2021)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();
    }

    @Test
    void shouldReturnVehiclesWhenFound() {
        // Arrange
        List<UUID> ids = Arrays.asList(vehicleId1, vehicleId2);
        when(vehicleRepository.findAllById(ids)).thenReturn(Arrays.asList(vehicle1, vehicle2));

        // Act
        List<VehicleResponseDTO> result = useCase.execute(ids).getData();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(vehicleId1, result.get(0).id());
        assertEquals("Toyota", result.get(0).brand());
        assertEquals("Corolla", result.get(0).model());

        assertEquals(vehicleId2, result.get(1).id());
        assertEquals("Honda", result.get(1).brand());
        assertEquals("Civic", result.get(1).model());

        verify(vehicleRepository, times(1)).findAllById(ids);
    }

    @Test
    void shouldReturnEmptyListWhenNoVehiclesFound() {
        // Arrange
        List<UUID> ids = Arrays.asList(vehicleId1, vehicleId2);
        when(vehicleRepository.findAllById(ids)).thenReturn(Collections.emptyList());

        // Act
        List<VehicleResponseDTO> result = useCase.execute(ids).getData();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(vehicleRepository, times(1)).findAllById(ids);
    }
}
