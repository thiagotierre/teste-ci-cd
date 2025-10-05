package com.fiap.challenge.parts.useCases.update;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.dto.UpdatePartRequestDTO;
import com.fiap.challenge.parts.useCases.find.FindPartByIdUseCase;
import com.fiap.challenge.shared.model.ResponseApi;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReturnPartsToStockUseCaseImplTest {

    @Mock
    private FindPartByIdUseCase findPartByIdUseCase;

    @Mock
    private UpdatePartUseCase updatePartUseCase;

    @InjectMocks
    private ReturnPartsToStockUseCaseImpl useCase;

    private UUID partId;
    private PartResponseDTO part;

    @BeforeEach
    void setUp() {
        partId = UUID.randomUUID();
        part = new PartResponseDTO(
                partId,
                "Filtro de Óleo",
                "Filtro OEM",
                new BigDecimal("39.90"),
                10, // stockQuantity
                5,  // reservedStock
                2,  // minimumStock
                null,
                null
        );
    }

    @Test
    void shouldThrowWhenPartNotFound() {
        when(findPartByIdUseCase.execute(partId))
                .thenReturn(new ResponseApi<PartResponseDTO>().of(null, null, null));

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> useCase.execute(partId, 3));

        assertTrue(ex.getMessage().contains("Part not found with ID: " + partId));
        verifyNoInteractions(updatePartUseCase);
    }

    @Test
    void shouldReturnTrueAndUpdateWhenReservedStockIsEnough() {
        when(findPartByIdUseCase.execute(partId))
                .thenReturn(new ResponseApi<PartResponseDTO>().of(null, null, part));

        boolean result = useCase.execute(partId, 3);

        assertTrue(result);

        // Captura os argumentos passados para o update
        ArgumentCaptor<UpdatePartRequestDTO> captor = ArgumentCaptor.forClass(UpdatePartRequestDTO.class);
        verify(updatePartUseCase).execute(eq(partId), captor.capture());

        UpdatePartRequestDTO updated = captor.getValue();
        assertEquals(part.stockQuantity() + 3, updated.stockQuantity());
        assertEquals(part.reservedStock() - 3, updated.reservedStock());
        assertEquals(part.name(), updated.name());
        assertEquals(part.description(), updated.description());
        assertEquals(part.price(), updated.price());
        assertEquals(part.minimumStock(), updated.minimumStock());
    }

    @Test
    void shouldReturnFalseWhenReservedStockIsNotEnough() {
        part = new PartResponseDTO(
                partId,
                "Filtro de Óleo",
                "Filtro OEM",
                new BigDecimal("39.90"),
                10,
                2, // reservedStock menor que quantity
                2,
                null,
                null
        );

        when(findPartByIdUseCase.execute(partId))
                .thenReturn(new ResponseApi<PartResponseDTO>().of(null, null, part));

        boolean result = useCase.execute(partId, 3);

        assertFalse(result);
        verifyNoInteractions(updatePartUseCase);
    }
}