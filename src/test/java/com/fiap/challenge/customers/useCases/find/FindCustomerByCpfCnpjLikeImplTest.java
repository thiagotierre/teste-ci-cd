package com.fiap.challenge.customers.useCases.find;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindCustomerByCpfCnpjLikeImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private FindCustomerByCpfCnpjLikeImpl useCase;

    @Test
    void shouldReturnMappedListPreservingOrder() {
        // Arrange
        String fragment = "123";
        var now = OffsetDateTime.now();

        var id1 = UUID.randomUUID();
        var m1 = new CustomerModel();
        m1.setId(id1);
        m1.setName("Maria");
        m1.setCpfCnpj("12345678901");
        m1.setPhone("1111");
        m1.setEmail("maria@ex.com");
        m1.setCreatedAt(now);
        m1.setUpdatedAt(now);

        var id2 = UUID.randomUUID();
        var m2 = new CustomerModel();
        m2.setId(id2);
        m2.setName("João");
        m2.setCpfCnpj("12300000000");
        m2.setPhone("2222");
        m2.setEmail("joao@ex.com");
        m2.setCreatedAt(now);
        m2.setUpdatedAt(now);

        when(customerRepository.findByCpfCnpjContainingWithCustomSort(fragment))
                .thenReturn(List.of(m1, m2)); // ordem esperada

        // Act
        List<CustomerResponseDTO> result = useCase.execute(fragment).getData();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        var r1 = result.get(0);
        assertEquals(id1, r1.id());
        assertEquals("Maria", r1.name());
        assertEquals("12345678901", r1.cpfCnpj());
        assertEquals("1111", r1.phone());
        assertEquals("maria@ex.com", r1.email());
        assertEquals(now, r1.createdAt());
        assertEquals(now, r1.updatedAt());

        var r2 = result.get(1);
        assertEquals(id2, r2.id());
        assertEquals("João", r2.name());
        assertEquals("12300000000", r2.cpfCnpj());
        assertEquals("2222", r2.phone());
        assertEquals("joao@ex.com", r2.email());
        assertEquals(now, r2.createdAt());
        assertEquals(now, r2.updatedAt());

        verify(customerRepository, times(1))
                .findByCpfCnpjContainingWithCustomSort(fragment);
    }

    @Test
    void shouldReturnEmptyListWhenRepositoryReturnsEmpty() {
        // Arrange
        String fragment = "999";
        when(customerRepository.findByCpfCnpjContainingWithCustomSort(fragment))
                .thenReturn(List.of());

        // Act
        List<CustomerResponseDTO> result = useCase.execute(fragment).getData();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1))
                .findByCpfCnpjContainingWithCustomSort(fragment);
    }
}
