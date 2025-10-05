package com.fiap.challenge.it;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.customers.useCases.delete.DeleteCustomerUseCase;
import com.fiap.challenge.shared.exception.customer.CustomerDeletionException;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
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

import java.util.UUID;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerDeletionFlowIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void dbProps(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", postgres::getJdbcUrl);
        r.add("spring.datasource.username", postgres::getUsername);
        r.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired CustomerRepository customerRepository;
    @Autowired VehicleRepository vehicleRepository;
    @Autowired DeleteCustomerUseCase deleteCustomer;

    @Test
    void shouldDeleteCustomerWithoutRelations() {
        // given
        CustomerModel c = new CustomerModel();
        c.setName("Cliente Sem Vínculo");
        c.setCpfCnpj("11122233344");
        c.setPhone("11999990000");
        c.setEmail("sem.vinculo@exemplo.com");
        c = customerRepository.save(c);
        UUID id = c.getId();

        // when
        deleteCustomer.execute(id);

        // then
        Assertions.assertTrue(customerRepository.findById(id).isEmpty());
    }

    @Test
    void shouldFailDeletingCustomerWithVehicle() {
        // given
        CustomerModel c = new CustomerModel();
        c.setName("Cliente Com Veículo");
        c.setCpfCnpj("55566677788");
        c.setPhone("11988887777");
        c.setEmail("com.veiculo@exemplo.com");
        c = customerRepository.save(c);

        VehicleModel v = new VehicleModel();
        v.setBrand("VW");
        v.setModel("Gol");
        v.setYear(2019);
        v.setLicensePlate("ABC-1234");
        v.setCustomer(c);
        vehicleRepository.save(v);

        // when + then
        UUID id = c.getId();
        Assertions.assertThrows(CustomerDeletionException.class, () -> deleteCustomer.execute(id));

        // customer continua existindo
        Assertions.assertTrue(customerRepository.findById(id).isPresent());
    }
}
