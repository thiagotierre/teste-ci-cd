package com.fiap.challenge.it;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.repository.UserRepository;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.WorkOrderPartModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.repository.WorkOrderPartRepository;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkOrderCreationFlowIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void dbProps(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", postgres::getJdbcUrl);
        r.add("spring.datasource.username", postgres::getUsername);
        r.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired CustomerRepository customerRepo;
    @Autowired VehicleRepository vehicleRepo;
    @Autowired UserRepository userRepo;
    @Autowired PartsRepository partsRepo;
    @Autowired WorkOrderRepository workOrderRepo;
    @Autowired WorkOrderPartRepository workOrderPartRepo;

    @Test
    void shouldCreateWorkOrderWithPartHappyPath() {
        // Cliente
        CustomerModel customer = customerRepo.save(CustomerModel.builder()
                .name("Cliente Happy")
                .cpfCnpj("11122233344")
                .phone("11999990000")
                .email("happy@ex.com")
                .build());

        // Veículo
        VehicleModel vehicle = new VehicleModel();
        vehicle.setBrand("VW");
        vehicle.setModel("Gol");
        vehicle.setYear(2019);
        vehicle.setLicensePlate("HAP-1234");
        vehicle.setCustomer(customer);
        vehicle = vehicleRepo.save(vehicle);

        // Usuário seed
        UserModel createdBy = userRepo.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Seed user not found"));

        // Peça
        PartModel part = partsRepo.save(PartModel.builder()
                .name("Filtro de Óleo")
                .description("OEM 1.6")
                .price(new BigDecimal("39.90"))
                .stockQuantity(20)
                .reservedStock(0)
                .minimumStock(2)
                .build());

        // Vamos vincular 2 unidades dessa peça
        int qty = 2;
        BigDecimal expectedTotal = part.getPrice().multiply(BigDecimal.valueOf(qty));

        // OS (defina o total ANTES do primeiro save e não salve de novo depois de inserir a peça)
        WorkOrderModel wo = new WorkOrderModel();
        wo.setCustomer(customer);
        wo.setVehicle(vehicle);
        wo.setCreatedBy(createdBy);
        wo.setStatus(WorkOrderStatus.RECEIVED);
        wo.setTotalAmount(expectedTotal);
        wo = workOrderRepo.save(wo);

        // Associação peça-OS
        WorkOrderPartModel wop = new WorkOrderPartModel();
        wop.setWorkOrder(wo);
        wop.setPart(part);
        wop.setQuantity(qty);
        wop.setUnitPrice(part.getPrice());
        wop = workOrderPartRepo.save(wop);

        // -------- Asserts
        Assertions.assertNotNull(wo.getId());

        WorkOrderModel reloaded = workOrderRepo.findById(wo.getId()).orElseThrow();
        Assertions.assertEquals(customer.getId(), reloaded.getCustomer().getId());
        Assertions.assertEquals(vehicle.getId(), reloaded.getVehicle().getId());
        Assertions.assertEquals(createdBy.getId(), reloaded.getCreatedBy().getId());
        Assertions.assertEquals(WorkOrderStatus.RECEIVED, reloaded.getStatus());
        Assertions.assertEquals(0, expectedTotal.compareTo(reloaded.getTotalAmount()));

        WorkOrderPartModel reloadedWop = workOrderPartRepo.findById(wop.getId()).orElseThrow();
        Assertions.assertEquals(wo.getId(), reloadedWop.getWorkOrder().getId());
        Assertions.assertEquals(part.getId(), reloadedWop.getPart().getId());
        Assertions.assertEquals(qty, reloadedWop.getQuantity());
    }
}
