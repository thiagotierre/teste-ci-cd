package com.fiap.challenge.customers.entity;

import com.fiap.challenge.customers.repository.CustomerRepository;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;NON_KEYWORDS=YEAR",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
class CustomerModelJpaTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldPersistWithIdAndTimestamps() {
        var c = CustomerModel.builder()
                .name("Maria")
                .cpfCnpj("12345678901")
                .phone("11999998888")
                .email("maria@exemplo.com")
                .build();

        var saved = customerRepository.saveAndFlush(c);

        assertNotNull(saved.getId());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
        assertEquals("Maria", saved.getName());
        assertEquals("12345678901", saved.getCpfCnpj());
    }

    @Test
    void shouldUpdateAndRefreshUpdatedAt() throws Exception {
        var c = CustomerModel.builder()
                .name("Fulano")
                .cpfCnpj("00011122233")
                .phone("1100000000")
                .email("fulano@exemplo.com")
                .build();

        var saved = customerRepository.saveAndFlush(c);
        OffsetDateTime createdAt = saved.getCreatedAt();
        OffsetDateTime firstUpdatedAt = saved.getUpdatedAt();

        Thread.sleep(5); // ajuda em precisão de timestamp

        saved.setName("Fulano Atualizado");
        var updated = customerRepository.saveAndFlush(saved);

        assertEquals(createdAt, updated.getCreatedAt());
        assertTrue(updated.getUpdatedAt().isAfter(firstUpdatedAt));
        assertEquals("Fulano Atualizado", updated.getName());
    }
}
