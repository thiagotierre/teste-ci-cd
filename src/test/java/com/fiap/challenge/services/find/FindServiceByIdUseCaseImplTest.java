package com.fiap.challenge.services.find;

import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.services.entity.ServiceModel;
import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.services.useCases.find.FindServiceByIdUseCaseImpl;
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
public class FindServiceByIdUseCaseImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private FindServiceByIdUseCaseImpl useCase;

    private UUID serviceId;
    private ServiceModel existingServiceModel;

    @BeforeEach
    void setUp() {

        serviceId = UUID.randomUUID();

        existingServiceModel = ServiceModel.builder()
                .id(serviceId)
                .name("Test Service")
                .description("Test Description")
                .basePrice(new BigDecimal("200.00"))
                .estimatedTimeMin(60)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();
    }

    @Test
    void shouldReturnServiceWhenFound() {
        // Arrange
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.of(existingServiceModel));

        // Act
        ServiceResponseDTO response = useCase.execute(serviceId).getData();

        // Assert
        assertNotNull(response);
        assertEquals(serviceId, response.id());
        assertEquals(existingServiceModel.getName(), response.name());
        assertEquals(existingServiceModel.getDescription(), response.description());
        assertEquals(existingServiceModel.getBasePrice(), response.basePrice());
        assertEquals(existingServiceModel.getEstimatedTimeMin(), response.estimatedTimeMin());

        verify(serviceRepository, times(1)).findById(serviceId);
    }

    @Test
    void shouldThrowExceptionWhenServiceNotFound() {
        // Arrange
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.empty());

        // Act & Assert
        ServiceNotFoundException exception = assertThrows(ServiceNotFoundException.class,
                () -> useCase.execute(serviceId));
        assertTrue(exception.getMessage().contains(serviceId.toString()));

        verify(serviceRepository, times(1)).findById(serviceId);
    }
}
