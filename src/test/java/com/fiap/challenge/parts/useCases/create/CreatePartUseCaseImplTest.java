package com.fiap.challenge.parts.useCases.create;

import com.fiap.challenge.parts.dto.CreatePartRequestDTO;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePartUseCaseImplTest {

    @Mock
    private PartsRepository partsRepository;

    @InjectMocks
    private CreatePartUseCaseImpl useCase;

    @Test
    void shouldSavePartAndReturnResponseDTO() {
        // Arrange
        var req = new CreatePartRequestDTO(
                "Filtro de Óleo",
                "Filtro OEM para motor 1.6",
                new BigDecimal("39.90"),
                25,
                5
        );

        var generatedId = UUID.randomUUID();
        var now = OffsetDateTime.now();

        // Simula o save preenchendo id/timestamps e default de reservedStock (se necessário)
        when(partsRepository.save(any(PartModel.class))).thenAnswer(inv -> {
            PartModel m = inv.getArgument(0);
            m.setId(generatedId);
            m.setCreatedAt(now);
            m.setUpdatedAt(now);
            return m;
        });

        // Act
        PartResponseDTO resp = useCase.execute(req).getData();

        // Assert — DTO retornado
        assertNotNull(resp);
        assertEquals(generatedId, resp.id());
        assertEquals("Filtro de Óleo", resp.name());
        assertEquals("Filtro OEM para motor 1.6", resp.description());
        assertEquals(new BigDecimal("39.90"), resp.price());
        assertEquals(25, resp.stockQuantity());
        assertEquals(0, resp.reservedStock()); // padrão esperado
        assertEquals(5, resp.minimumStock());
        assertEquals(now, resp.createdAt());
        assertEquals(now, resp.updatedAt());

        // Assert — entidade enviada ao repository
        verify(partsRepository).save(argThat(m ->
                "Filtro de Óleo".equals(m.getName()) &&
                        "Filtro OEM para motor 1.6".equals(m.getDescription()) &&
                        new BigDecimal("39.90").compareTo(m.getPrice()) == 0 &&
                        Integer.valueOf(25).equals(m.getStockQuantity()) &&
                        Integer.valueOf(5).equals(m.getMinimumStock()) &&
                        Integer.valueOf(0).equals(m.getReservedStock()) // <- agora validamos aqui
        ));
    }
}
