package com.fiap.challenge.services.create;

import com.fiap.challenge.services.dto.InputServiceDTO;
import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.services.entity.ServiceModel;
import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.services.useCases.create.CreateServiceUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateServiceUseCaseImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private CreateServiceUseCaseImpl useCase;

    private InputServiceDTO inputDTO;
    private ServiceModel savedServiceModel;

    @BeforeEach
    void setUp() {

        inputDTO = new InputServiceDTO(
                "Troca de Óleo Change",
                "Troca de Óleo completa",
                new BigDecimal("120.0"),
                60
        );

        savedServiceModel = ServiceModel.builder()
                .id(UUID.randomUUID())
                .name(inputDTO.name())
                .description(inputDTO.description())
                .basePrice(inputDTO.basePrice())
                .estimatedTimeMin(inputDTO.estimatedTimeMin())
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();
    }

    @Test
    void shouldCreateServiceSuccessfully() {
        // Arrange
        when(serviceRepository.save(any(ServiceModel.class))).thenReturn(savedServiceModel);

        // Act
        ServiceResponseDTO response = useCase.execute(inputDTO).getData();

        // Assert
        assertNotNull(response);
        assertEquals(savedServiceModel.getId(), response.id());
        assertEquals(inputDTO.name(), response.name());
        assertEquals(inputDTO.description(), response.description());
        assertEquals(inputDTO.basePrice(), response.basePrice());
        assertEquals(inputDTO.estimatedTimeMin(), response.estimatedTimeMin());
        assertNotNull(response.createdAt());
        assertNotNull(response.updatedAt());

        verify(serviceRepository, times(1)).save(any(ServiceModel.class));
    }
}
