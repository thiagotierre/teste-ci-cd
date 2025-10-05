package com.fiap.challenge.services.find;

import com.fiap.challenge.services.entity.ServiceModel;
import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.services.useCases.find.FindServiceModelByIdUseCaseImpl;
import com.fiap.challenge.shared.exception.serice.ServiceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindServiceModelByIdUseCaseImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private FindServiceModelByIdUseCaseImpl useCase;

    private UUID serviceId;
    private ServiceModel existingServiceModel;

    @BeforeEach
    void setUp() {

        serviceId = UUID.randomUUID();

        existingServiceModel = ServiceModel.builder()
                .id(serviceId)
                .name("Test Service")
                .description("Test Description")
                .basePrice(new BigDecimal("250.00"))
                .estimatedTimeMin(90)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();
    }

    @Test
    void shouldReturnServiceModelWhenFound() {
        // Arrange
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.of(existingServiceModel));

        // Act
        ServiceModel result = useCase.execute(serviceId);

        // Assert
        assertNotNull(result);
        assertEquals(serviceId, result.getId());
        assertEquals(existingServiceModel.getName(), result.getName());
        assertEquals(existingServiceModel.getDescription(), result.getDescription());
        assertEquals(0, existingServiceModel.getBasePrice().compareTo(result.getBasePrice()));
        assertEquals(existingServiceModel.getEstimatedTimeMin(), result.getEstimatedTimeMin());

        verify(serviceRepository, times(1)).findById(serviceId);
    }

    @Test
    void shouldThrowExceptionWhenServiceModelNotFound() {
        // Arrange
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.empty());

        // Act & Assert
        ServiceNotFoundException exception = assertThrows(ServiceNotFoundException.class,
                () -> useCase.execute(serviceId));
        assertTrue(exception.getMessage().contains(serviceId.toString()));

        verify(serviceRepository, times(1)).findById(serviceId);
    }
}
