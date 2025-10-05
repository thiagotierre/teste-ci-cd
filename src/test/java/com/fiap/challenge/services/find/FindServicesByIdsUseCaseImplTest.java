package com.fiap.challenge.services.find;

import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.services.entity.ServiceModel;
import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.services.useCases.find.FindServicesByIdsUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindServicesByIdsUseCaseImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private FindServicesByIdsUseCaseImpl useCase;

    private UUID serviceId1;
    private UUID serviceId2;
    private ServiceModel serviceModel1;
    private ServiceModel serviceModel2;

    @BeforeEach
    void setUp() {

        serviceId1 = UUID.randomUUID();
        serviceId2 = UUID.randomUUID();

        serviceModel1 = ServiceModel.builder()
                .id(serviceId1)
                .name("Service One")
                .description("Description One")
                .basePrice(new BigDecimal("100.00"))
                .estimatedTimeMin(30)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        serviceModel2 = ServiceModel.builder()
                .id(serviceId2)
                .name("Service Two")
                .description("Description Two")
                .basePrice(new BigDecimal("200.00"))
                .estimatedTimeMin(60)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();
    }

    @Test
    void shouldReturnListOfServiceResponseDTOs() {
        // Arrange
        List<UUID> ids = Arrays.asList(serviceId1, serviceId2);
        when(serviceRepository.findAllById(ids)).thenReturn(Arrays.asList(serviceModel1, serviceModel2));

        // Act
        List<ServiceResponseDTO> responses = useCase.execute(ids).getData();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());

        ServiceResponseDTO dto1 = responses.get(0);
        assertEquals(serviceId1, dto1.id());
        assertEquals(serviceModel1.getName(), dto1.name());
        assertEquals(serviceModel1.getDescription(), dto1.description());
        assertEquals(0, serviceModel1.getBasePrice().compareTo(dto1.basePrice()));
        assertEquals(serviceModel1.getEstimatedTimeMin(), dto1.estimatedTimeMin());

        ServiceResponseDTO dto2 = responses.get(1);
        assertEquals(serviceId2, dto2.id());
        assertEquals(serviceModel2.getName(), dto2.name());
        assertEquals(serviceModel2.getDescription(), dto2.description());
        assertEquals(0, serviceModel2.getBasePrice().compareTo(dto2.basePrice()));
        assertEquals(serviceModel2.getEstimatedTimeMin(), dto2.estimatedTimeMin());

        verify(serviceRepository, times(1)).findAllById(ids);
    }
}
