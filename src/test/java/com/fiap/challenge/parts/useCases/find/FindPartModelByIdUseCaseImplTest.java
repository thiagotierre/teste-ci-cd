package com.fiap.challenge.parts.useCases.find;

import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.shared.exception.part.PartNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindPartModelByIdUseCaseImplTest {

    @Mock
    private PartsRepository partsRepository;

    @InjectMocks
    private FindPartModelByIdUseCaseImpl useCase;

    @Test
    void shouldReturnPartModelWhenFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        PartModel model = new PartModel();
        model.setId(id);
        model.setName("Filtro de Combustível");
        model.setDescription("Filtro compatível com modelo X");
        model.setPrice(new BigDecimal("59.90"));
        model.setStockQuantity(10);
        model.setReservedStock(1);
        model.setMinimumStock(2);
        model.setCreatedAt(now);
        model.setUpdatedAt(now);

        when(partsRepository.findById(id)).thenReturn(Optional.of(model));

        // Act
        PartModel result = useCase.execute(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Filtro de Combustível", result.getName());
        assertEquals("Filtro compatível com modelo X", result.getDescription());
        assertEquals(new BigDecimal("59.90"), result.getPrice());
        assertEquals(10, result.getStockQuantity());
        assertEquals(1, result.getReservedStock());
        assertEquals(2, result.getMinimumStock());
        assertEquals(now, result.getCreatedAt());
        assertEquals(now, result.getUpdatedAt());

        verify(partsRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowWhenPartNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(partsRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(PartNotFoundException.class, () -> useCase.execute(id));
        verify(partsRepository, times(1)).findById(id);
    }
}
