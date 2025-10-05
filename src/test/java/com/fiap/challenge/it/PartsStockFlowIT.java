package com.fiap.challenge.it;

import com.fiap.challenge.parts.dto.CreatePartRequestDTO;
import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.useCases.create.CreatePartUseCase;
import com.fiap.challenge.parts.useCases.find.FindPartByIdUseCase;
import com.fiap.challenge.parts.useCases.update.ReturnPartsToStockUseCase;
import com.fiap.challenge.parts.useCases.update.SubtractPartsFromStockUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PartsStockFlowIT {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void dbProps(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", postgres::getJdbcUrl);
        r.add("spring.datasource.username", postgres::getUsername);
        r.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired CreatePartUseCase createPart;
    @Autowired SubtractPartsFromStockUseCase subtractStock;
    @Autowired ReturnPartsToStockUseCase returnStock;
    @Autowired FindPartByIdUseCase findPart;

    @Test
    void endToEnd_part_stock_reserve_and_return() {
        // 1) cria a peça
        PartResponseDTO created = createPart.execute(new CreatePartRequestDTO(
                "Filtro de Óleo", "Filtro OEM", new BigDecimal("39.90"),
                10, 2
        )).getData();
        UUID partId = created.id();

        // 2) reserva 3 (stock=7, reserved=3)
        boolean reserved = subtractStock.execute(partId, 3);
        Assertions.assertTrue(reserved);

        PartResponseDTO afterReserve = findPart.execute(partId).getData();
        Assertions.assertEquals(7, afterReserve.stockQuantity());
        Assertions.assertEquals(3, afterReserve.reservedStock());

        // 3) devolve 2 (stock=9, reserved=1)
        boolean returned = returnStock.execute(partId, 2);
        Assertions.assertTrue(returned);

        PartResponseDTO afterReturn = findPart.execute(partId).getData();
        Assertions.assertEquals(9, afterReturn.stockQuantity());
        Assertions.assertEquals(1, afterReturn.reservedStock());
    }
}
