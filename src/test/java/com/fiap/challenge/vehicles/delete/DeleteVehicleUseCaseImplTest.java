package com.fiap.challenge.vehicles.delete;

import com.fiap.challenge.shared.exception.vehicle.VehicleNotFoundException;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.vehicles.useCases.delete.DeleteVehicleUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteVehicleUseCaseImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private DeleteVehicleUseCaseImpl useCase;

    private UUID vehicleId;

    @BeforeEach
    void setUp() {
        vehicleId = UUID.randomUUID();
    }

    @Test
    void shouldDeleteVehicleWhenExists() {
        // Arrange
        when(vehicleRepository.existsById(vehicleId)).thenReturn(true);

        // Act
        useCase.execute(vehicleId);

        // Assert
        verify(vehicleRepository, times(1)).existsById(vehicleId);
        verify(vehicleRepository, times(1)).deleteById(vehicleId);
    }

    @Test
    void shouldThrowExceptionWhenVehicleNotFound() {
        // Arrange
        when(vehicleRepository.existsById(vehicleId)).thenReturn(false);

        // Act & Assert
        assertThrows(VehicleNotFoundException.class, () -> useCase.execute(vehicleId));

        verify(vehicleRepository, times(1)).existsById(vehicleId);
        verify(vehicleRepository, never()).deleteById(any());
    }
}
