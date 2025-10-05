package com.fiap.challenge.services.update;

import com.fiap.challenge.services.dto.InputServiceDTO;
import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.services.entity.ServiceModel;
import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.services.useCases.update.UpdateServiceUseCaseImpl;
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
public class UpdateServiceUseCaseImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private UpdateServiceUseCaseImpl useCase;

    private UUID serviceId;
    private ServiceModel existingServiceModel;
    private InputServiceDTO inputDTO;

    @BeforeEach
    void setUp() {

        serviceId = UUID.randomUUID();

        existingServiceModel = ServiceModel.builder()
                .id(serviceId)
                .name("Old Service")
                .description("Old Description")
                .basePrice(new BigDecimal("100.0"))
                .estimatedTimeMin(30)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        inputDTO = new InputServiceDTO(
                "New Service",
                "New Description",
                new BigDecimal("150.0"),
                45
        );
    }

    @Test
    void shouldUpdateServiceSuccessfully() {
        // Arrange
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.of(existingServiceModel));
        when(serviceRepository.save(any(ServiceModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ServiceResponseDTO response = useCase.execute(serviceId, inputDTO).getData();

        // Assert
        assertNotNull(response);
        assertEquals(serviceId, response.id());
        assertEquals(inputDTO.name(), response.name());
        assertEquals(inputDTO.description(), response.description());
        assertEquals(inputDTO.basePrice(), response.basePrice());
        assertEquals(inputDTO.estimatedTimeMin(), response.estimatedTimeMin());

        verify(serviceRepository).findById(serviceId);
        verify(serviceRepository).save(existingServiceModel);
    }

    @Test
    void shouldThrowExceptionWhenServiceNotFound() {
        // Arrange
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.empty());

        // Act & Assert
        ServiceNotFoundException exception = assertThrows(ServiceNotFoundException.class,
                () -> useCase.execute(serviceId, inputDTO));
        assertTrue(exception.getMessage().contains(serviceId.toString()));

        verify(serviceRepository).findById(serviceId);
        verify(serviceRepository, never()).save(any());
    }
}
