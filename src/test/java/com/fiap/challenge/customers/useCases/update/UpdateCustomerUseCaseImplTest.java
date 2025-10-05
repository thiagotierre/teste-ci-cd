package com.fiap.challenge.customers.useCases.update;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerAlreadyExistsException;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;
import com.fiap.challenge.users.dto.UpdateCustomerRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerUseCaseImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private UpdateCustomerUseCaseImpl useCase;

    @Test
    void shouldThrowWhenCustomerNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        var req = new UpdateCustomerRequestDTO("Novo Nome", "123", "1199", "novo@ex.com");
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(CustomerNotFoundException.class, () -> useCase.execute(id, req));

        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, never()).save(any());
    }

    @Test
    void shouldUpdateWhenCpfUnchanged() {
        // Arrange
        UUID id = UUID.randomUUID();
        OffsetDateTime created = OffsetDateTime.now().minusDays(1);
        OffsetDateTime updated = OffsetDateTime.now();

        CustomerModel existing = new CustomerModel();
        existing.setId(id);
        existing.setName("Antigo Nome");
        existing.setCpfCnpj("12345678901");
        existing.setPhone("1111");
        existing.setEmail("antigo@ex.com");
        existing.setCreatedAt(created);
        existing.setUpdatedAt(created);

        var req = new UpdateCustomerRequestDTO("Novo Nome", "12345678901", "2222", "novo@ex.com");

        when(customerRepository.findById(id)).thenReturn(Optional.of(existing));
        when(customerRepository.save(any(CustomerModel.class))).thenAnswer(inv -> {
            CustomerModel m = inv.getArgument(0);
            m.setUpdatedAt(updated);
            return m;
        });

        // Act
        CustomerResponseDTO resp = useCase.execute(id, req).getData();

        // Assert
        assertNotNull(resp);
        assertEquals(id, resp.id());
        assertEquals("Novo Nome", resp.name());
        assertEquals("12345678901", resp.cpfCnpj());
        assertEquals("2222", resp.phone());
        assertEquals("novo@ex.com", resp.email());
        assertEquals(created, resp.createdAt());
        assertNotNull(resp.updatedAt());

        // Verifica que NÃO consultou unicidade (pois CPF não mudou)
        verify(customerRepository, never()).findByCpfCnpjAndIdNot(anyString(), any(UUID.class));

        // Verifica que salvou com os campos atualizados
        ArgumentCaptor<CustomerModel> captor = ArgumentCaptor.forClass(CustomerModel.class);
        verify(customerRepository).save(captor.capture());
        CustomerModel saved = captor.getValue();
        assertEquals("Novo Nome", saved.getName());
        assertEquals("12345678901", saved.getCpfCnpj());
        assertEquals("2222", saved.getPhone());
        assertEquals("novo@ex.com", saved.getEmail());
    }

    @Test
    void shouldUpdateWhenCpfChangedAndIsUnique() {
        // Arrange
        UUID id = UUID.randomUUID();
        OffsetDateTime created = OffsetDateTime.now().minusDays(2);

        CustomerModel existing = new CustomerModel();
        existing.setId(id);
        existing.setName("Fulano");
        existing.setCpfCnpj("11111111111");
        existing.setPhone("1111");
        existing.setEmail("fulano@ex.com");
        existing.setCreatedAt(created);
        existing.setUpdatedAt(created);

        var req = new UpdateCustomerRequestDTO("Ciclano", "22222222222", "2222", "ciclano@ex.com");

        when(customerRepository.findById(id)).thenReturn(Optional.of(existing));
        when(customerRepository.findByCpfCnpjAndIdNot("22222222222", id)).thenReturn(Optional.empty());
        when(customerRepository.save(any(CustomerModel.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        CustomerResponseDTO resp = useCase.execute(id, req).getData();

        // Assert
        assertNotNull(resp);
        assertEquals(id, resp.id());
        assertEquals("Ciclano", resp.name());
        assertEquals("22222222222", resp.cpfCnpj());
        assertEquals("2222", resp.phone());
        assertEquals("ciclano@ex.com", resp.email());
        assertEquals(created, resp.createdAt());

        verify(customerRepository).findByCpfCnpjAndIdNot("22222222222", id);
        verify(customerRepository).save(any(CustomerModel.class));
    }

    @Test
    void shouldThrowWhenCpfChangedAndAlreadyUsedByAnother() {
        // Arrange
        UUID id = UUID.randomUUID();

        CustomerModel existing = new CustomerModel();
        existing.setId(id);
        existing.setName("Fulano");
        existing.setCpfCnpj("11111111111");
        existing.setPhone("1111");
        existing.setEmail("fulano@ex.com");

        var req = new UpdateCustomerRequestDTO("Fulano Atualizado", "22222222222", "3333", "novo@ex.com");

        CustomerModel other = new CustomerModel(); // qualquer entidade para sinalizar conflito

        when(customerRepository.findById(id)).thenReturn(Optional.of(existing));
        when(customerRepository.findByCpfCnpjAndIdNot("22222222222", id))
                .thenReturn(Optional.of(other));

        // Act + Assert
        assertThrows(CustomerAlreadyExistsException.class, () -> useCase.execute(id, req));

        verify(customerRepository).findById(id);
        verify(customerRepository).findByCpfCnpjAndIdNot("22222222222", id);
        verify(customerRepository, never()).save(any());
    }
}
