package com.fiap.challenge.parts.useCases.update;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.dto.UpdatePartRequestDTO;
import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.shared.exception.part.PartNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
class UpdatePartUseCaseImplTest {

    @Mock
    private PartsRepository partsRepository;

    @InjectMocks
    private UpdatePartUseCaseImpl useCase;

    @Test
    void shouldThrowWhenPartNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        var req = UpdatePartRequestDTO.builder()
                .name("Novo nome")
                .description("Nova descrição")
                .price(new BigDecimal("10.00"))
                .stockQuantity(5)
                .reservedStock(1)
                .minimumStock(2)
                .build();

        when(partsRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(PartNotFoundException.class, () -> useCase.execute(id, req));
        verify(partsRepository, times(1)).findById(id);
        verify(partsRepository, never()).save(any());
    }

    @Test
    void shouldUpdateAndReturnResponseDTO() {
        // Arrange
        UUID id = UUID.randomUUID();
        OffsetDateTime created = OffsetDateTime.now().minusDays(1);
        OffsetDateTime updated = OffsetDateTime.now();

        PartModel existing = new PartModel();
        existing.setId(id);
        existing.setName("Antigo nome");
        existing.setDescription("Antiga descrição");
        existing.setPrice(new BigDecimal("20.00"));
        existing.setStockQuantity(10);
        existing.setReservedStock(3);
        existing.setMinimumStock(1);
        existing.setCreatedAt(created);
        existing.setUpdatedAt(created);

        var req = UpdatePartRequestDTO.builder()
                .name("Novo nome")
                .description("Nova descrição")
                .price(new BigDecimal("15.50"))
                .stockQuantity(8)
                .reservedStock(2)
                .minimumStock(4)
                .build();

        when(partsRepository.findById(id)).thenReturn(Optional.of(existing));
        when(partsRepository.save(any(PartModel.class))).thenAnswer(inv -> {
            PartModel m = inv.getArgument(0);
            m.setUpdatedAt(updated);
            return m;
        });

        // Act
        PartResponseDTO resp = useCase.execute(id, req).getData();

        // Assert DTO
        assertNotNull(resp);
        assertEquals(id, resp.id());
        assertEquals("Novo nome", resp.name());
        assertEquals("Nova descrição", resp.description());
        assertEquals(new BigDecimal("15.50"), resp.price());
        assertEquals(8, resp.stockQuantity());
        assertEquals(2, resp.reservedStock());
        assertEquals(4, resp.minimumStock());
        assertEquals(created, resp.createdAt());
        assertEquals(updated, resp.updatedAt());

        // Assert entidade enviada ao save
        ArgumentCaptor<PartModel> captor = ArgumentCaptor.forClass(PartModel.class);
        verify(partsRepository).save(captor.capture());
        PartModel saved = captor.getValue();
        assertEquals("Novo nome", saved.getName());
        assertEquals("Nova descrição", saved.getDescription());
        assertEquals(0, new BigDecimal("15.50").compareTo(saved.getPrice()));
        assertEquals(8, saved.getStockQuantity());
        assertEquals(2, saved.getReservedStock());
        assertEquals(4, saved.getMinimumStock());
    }
}
