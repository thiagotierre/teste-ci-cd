package com.fiap.challenge.services.delete;

import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.services.useCases.delete.DeleteServiceUseCaseImpl;
import com.fiap.challenge.shared.exception.serice.ServiceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteServiceUseCaseImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private DeleteServiceUseCaseImpl useCase;

    private UUID serviceId;

    @BeforeEach
    void setUp() {
        serviceId = UUID.randomUUID();
    }

    @Test
    void shouldDeleteServiceWhenExists() {
        // Arrange
        when(serviceRepository.existsById(serviceId)).thenReturn(true);
        doNothing().when(serviceRepository).deleteById(serviceId);

        // Act
        useCase.execute(serviceId);

        // Assert
        verify(serviceRepository, times(1)).existsById(serviceId);
        verify(serviceRepository, times(1)).deleteById(serviceId);
    }

    @Test
    void shouldThrowExceptionWhenServiceDoesNotExist() {
        // Arrange
        when(serviceRepository.existsById(serviceId)).thenReturn(false);

        // Act & Assert
        ServiceNotFoundException exception = assertThrows(ServiceNotFoundException.class, () -> useCase.execute(serviceId));
        assertTrue(exception.getMessage().contains(serviceId.toString()));

        verify(serviceRepository, times(1)).existsById(serviceId);
        verify(serviceRepository, never()).deleteById(any());
    }
}
