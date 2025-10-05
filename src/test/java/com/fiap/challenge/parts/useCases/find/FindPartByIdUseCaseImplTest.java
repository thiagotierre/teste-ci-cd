package com.fiap.challenge.parts.useCases.find;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.shared.exception.part.PartNotFoundException;
import com.fiap.challenge.shared.model.ResponseApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindPartByIdUseCaseImplTest {

    @Mock
    private PartsRepository partsRepository;

    @InjectMocks
    private FindPartByIdUseCaseImpl useCase;

    @Test
    void shouldReturnPartResponseWhenFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        PartModel model = new PartModel();
        model.setId(id);
        model.setName("Filtro de Óleo");
        model.setDescription("Filtro OEM");
        model.setPrice(new BigDecimal("39.90"));
        model.setStockQuantity(25);
        model.setReservedStock(3);
        model.setMinimumStock(5);
        model.setCreatedAt(now);
        model.setUpdatedAt(now);

        when(partsRepository.findById(id)).thenReturn(Optional.of(model));

        // Act
        PartResponseDTO resp = useCase.execute(id).getData();

        // Assert
        assertNotNull(resp);
        assertEquals(id, resp.id());
        assertEquals("Filtro de Óleo", resp.name());
        assertEquals("Filtro OEM", resp.description());
        assertEquals(new BigDecimal("39.90"), resp.price());
        assertEquals(25, resp.stockQuantity());
        assertEquals(3, resp.reservedStock());
        assertEquals(5, resp.minimumStock());
        assertEquals(now, resp.createdAt());
        assertEquals(now, resp.updatedAt());

        verify(partsRepository, times(1)).findById(id);
    }

    @Test
    void shouldReturnNotFoundWhenPartNotFound() {
        UUID id = UUID.randomUUID();
        when(partsRepository.findById(id)).thenReturn(Optional.empty());

        ResponseApi<PartResponseDTO> response = useCase.execute(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals("Part not found with ID: " + id, response.getMessage());
        assertNull(response.getData());

        verify(partsRepository, times(1)).findById(id);
    }
}
