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
class FindCustomersByIdsUseCaseImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private FindCustomersByIdsUseCaseImpl useCase;

    @Test
    void shouldReturnMappedListPreservingRepositoryOrder() {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();
        List<UUID> ids = List.of(id1, id2, id3);

        OffsetDateTime now = OffsetDateTime.now();

        CustomerModel c2 = new CustomerModel();
        c2.setId(id2);
        c2.setName("João");
        c2.setCpfCnpj("222");
        c2.setPhone("11");
        c2.setEmail("joao@ex.com");
        c2.setCreatedAt(now);
        c2.setUpdatedAt(now);

        CustomerModel c1 = new CustomerModel();
        c1.setId(id1);
        c1.setName("Maria");
        c1.setCpfCnpj("111");
        c1.setPhone("22");
        c1.setEmail("maria@ex.com");
        c1.setCreatedAt(now);
        c1.setUpdatedAt(now);

        // Simula repositório retornando em ordem diferente da lista de entrada
        when(customerRepository.findAllById(ids)).thenReturn(List.of(c2, c1));

        // Act
        List<CustomerResponseDTO> result = useCase.execute(ids).getData();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Mantém a ordem do repositório: primeiro c2, depois c1
        assertEquals(id2, result.get(0).id());
        assertEquals("João", result.get(0).name());
        assertEquals(id1, result.get(1).id());
        assertEquals("Maria", result.get(1).name());

        verify(customerRepository, times(1)).findAllById(ids);
    }

    @Test
    void shouldReturnEmptyListWhenRepositoryReturnsEmpty() {
        // Arrange
        List<UUID> ids = List.of(UUID.randomUUID(), UUID.randomUUID());
        when(customerRepository.findAllById(ids)).thenReturn(List.of());

        // Act
        List<CustomerResponseDTO> result = useCase.execute(ids).getData();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findAllById(ids);
    }

    @Test
    void shouldReturnPartialListWhenSomeIdsAreMissing() {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<UUID> ids = List.of(id1, id2);

        OffsetDateTime now = OffsetDateTime.now();

        CustomerModel onlyFound = new CustomerModel();
        onlyFound.setId(id2);
        onlyFound.setName("Ana");
        onlyFound.setCpfCnpj("999");
        onlyFound.setPhone("33");
        onlyFound.setEmail("ana@ex.com");
        onlyFound.setCreatedAt(now);
        onlyFound.setUpdatedAt(now);

        when(customerRepository.findAllById(ids)).thenReturn(List.of(onlyFound));

        // Act
        List<CustomerResponseDTO> result = useCase.execute(ids).getData();

        // Assert
        assertEquals(1, result.size());
        assertEquals(id2, result.get(0).id());
        assertEquals("Ana", result.get(0).name());

        verify(customerRepository, times(1)).findAllById(ids);
    }
}
