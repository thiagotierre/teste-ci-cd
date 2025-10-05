package com.fiap.challenge.parts.useCases.find;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.repository.PartsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindPartsByIdsUseCaseImplTest {

    @Mock
    private PartsRepository partsRepository;

    @InjectMocks
    private FindPartsByIdsUseCaseImpl useCase;

    @Test
    void shouldReturnMappedListPreservingRepositoryOrder() {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();
        List<UUID> ids = List.of(id1, id2, id3);

        OffsetDateTime now = OffsetDateTime.now();

        PartModel p2 = new PartModel();
        p2.setId(id2);
        p2.setName("Filtro de Ar");
        p2.setDescription("Elemento filtrante");
        p2.setPrice(new BigDecimal("29.90"));
        p2.setStockQuantity(50);
        p2.setReservedStock(5);
        p2.setMinimumStock(10);
        p2.setCreatedAt(now);
        p2.setUpdatedAt(now);

        PartModel p1 = new PartModel();
        p1.setId(id1);
        p1.setName("Correia Dentada");
        p1.setDescription("Correia 120 dentes");
        p1.setPrice(new BigDecimal("149.90"));
        p1.setStockQuantity(15);
        p1.setReservedStock(2);
        p1.setMinimumStock(3);
        p1.setCreatedAt(now);
        p1.setUpdatedAt(now);

        // Repositório retorna em ordem diferente da lista de entrada
        when(partsRepository.findAllById(ids)).thenReturn(List.of(p2, p1));

        // Act
        List<PartResponseDTO> result = useCase.execute(ids).getData();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Mantém a ordem do repositório: p2 primeiro
        assertEquals(id2, result.get(0).id());
        assertEquals("Filtro de Ar", result.get(0).name());
        assertEquals(id1, result.get(1).id());
        assertEquals("Correia Dentada", result.get(1).name());

        verify(partsRepository, times(1)).findAllById(ids);
    }

    @Test
    void shouldReturnEmptyListWhenRepositoryReturnsEmpty() {
        // Arrange
        List<UUID> ids = List.of(UUID.randomUUID(), UUID.randomUUID());
        when(partsRepository.findAllById(ids)).thenReturn(List.of());

        // Act
        List<PartResponseDTO> result = useCase.execute(ids).getData();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(partsRepository, times(1)).findAllById(ids);
    }

    @Test
    void shouldReturnPartialListWhenSomeIdsAreMissing() {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<UUID> ids = List.of(id1, id2);

        OffsetDateTime now = OffsetDateTime.now();

        PartModel found = new PartModel();
        found.setId(id2);
        found.setName("Velas de Ignição");
        found.setDescription("Jogo com 4 unidades");
        found.setPrice(new BigDecimal("89.90"));
        found.setStockQuantity(20);
        found.setReservedStock(0);
        found.setMinimumStock(4);
        found.setCreatedAt(now);
        found.setUpdatedAt(now);

        when(partsRepository.findAllById(ids)).thenReturn(List.of(found));

        // Act
        List<PartResponseDTO> result = useCase.execute(ids).getData();

        // Assert
        assertEquals(1, result.size());
        assertEquals(id2, result.get(0).id());
        assertEquals("Velas de Ignição", result.get(0).name());
        verify(partsRepository, times(1)).findAllById(ids);
    }
}
