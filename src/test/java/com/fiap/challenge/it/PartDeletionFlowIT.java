package com.fiap.challenge.it;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.parts.useCases.delete.DeletePartUseCase;
import com.fiap.challenge.shared.exception.part.PartDeletionException;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.repository.UserRepository;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.WorkOrderPartModel;
import com.fiap.challenge.workOrders.repository.WorkOrderPartRepository;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PartDeletionFlowIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void dbProps(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", postgres::getJdbcUrl);
        r.add("spring.datasource.username", postgres::getUsername);
        r.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired PartsRepository partsRepo;
    @Autowired DeletePartUseCase deletePart;

    @Autowired CustomerRepository customerRepo;
    @Autowired VehicleRepository vehicleRepo;
    @Autowired WorkOrderRepository workOrderRepo;
    @Autowired WorkOrderPartRepository workOrderPartRepo;
    @Autowired UserRepository userRepo; // novo

    @Test
    void shouldDeletePartWhenNotReferenced() {
        PartModel p = partsRepo.save(PartModel.builder()
                .name("Pastilha de Freio")
                .description("Dianteira")
                .price(new BigDecimal("99.90"))
                .stockQuantity(10)
                .reservedStock(0)
                .minimumStock(2)
                .build());

        final UUID partId = p.getId();

        // não deve lançar exceção
        Assertions.assertDoesNotThrow(() -> deletePart.execute(partId));

        // deve ter sido removida
        Assertions.assertTrue(partsRepo.findById(partId).isEmpty());
    }

    @Test
    void shouldFailWhenPartIsReferencedByWorkOrder() {
        // customer
        CustomerModel c = CustomerModel.builder()
                .name("Cliente OS")
                .cpfCnpj("12345678901")
                .phone("11999990000")
                .email("cliente@ex.com")
                .build();
        c = customerRepo.save(c);

        // vehicle
        VehicleModel v = new VehicleModel();
        v.setBrand("VW");
        v.setModel("Gol");
        v.setYear(2019);
        v.setLicensePlate("ABC-1234");
        v.setCustomer(c);
        v = vehicleRepo.save(v);

        // part
        PartModel p = PartModel.builder()
                .name("Pastilha de Freio")
                .description("Dianteira")
                .price(new BigDecimal("99.90"))
                .stockQuantity(10)
                .reservedStock(0)
                .minimumStock(2)
                .build();
        p = partsRepo.save(p);

        // usuário criado pelo Liquibase (admin)
        UserModel createdBy = userRepo.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Seed user not found"));

        // work order com campos NOT NULL preenchidos
        WorkOrderModel wo = new WorkOrderModel();
        wo.setCustomer(c);
        wo.setVehicle(v);
        wo.setCreatedBy(createdBy);
        wo.setStatus(WorkOrderStatus.RECEIVED);
        wo.setTotalAmount(BigDecimal.ZERO);
        wo = workOrderRepo.save(wo);

        // associação peça-OS
        WorkOrderPartModel wop = new WorkOrderPartModel();
        wop.setWorkOrder(wo);
        wop.setPart(p);
        wop.setQuantity(2);
        wop.setUnitPrice(p.getPrice());
        workOrderPartRepo.save(wop);

        final UUID partId = p.getId();

        // tentar deletar deve falhar porque a peça está referenciada numa OS
        Assertions.assertThrows(PartDeletionException.class, () -> deletePart.execute(partId));

        // peça continua no banco
        Assertions.assertTrue(partsRepo.findById(partId).isPresent());
    }
}
