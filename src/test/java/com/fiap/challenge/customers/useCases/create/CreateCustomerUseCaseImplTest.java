package com.fiap.challenge.customers.useCases.create;

import com.fiap.challenge.customers.dto.CreateCustomerRequestDTO;
import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateCustomerUseCaseImpl useCase;

    @Test
    void shouldThrowWhenCpfCnpjAlreadyExists() {
        var request = new CreateCustomerRequestDTO(
                "Maria Silva", "12345678901", "11999998888", "maria@exemplo.com"
        );
        when(customerRepository.existsByCpfCnpj("12345678901")).thenReturn(true);

        assertThrows(CustomerAlreadyExistsException.class, () -> useCase.execute(request));

        verify(customerRepository, times(1)).existsByCpfCnpj("12345678901");
        verify(customerRepository, never()).save(any(CustomerModel.class));
    }

    @Test
    void shouldSaveCustomerAndReturnResponseDTO() {

        var request = new CreateCustomerRequestDTO(
                "João Souza", "98765432100", "11911112222", "joao@exemplo.com"
        );
        when(customerRepository.existsByCpfCnpj("98765432100")).thenReturn(false);

        var generatedId = UUID.randomUUID();
        var now = OffsetDateTime.now();

        when(customerRepository.save(any(CustomerModel.class))).thenAnswer(inv -> {
            CustomerModel m = inv.getArgument(0);
            m.setId(generatedId);
            m.setCreatedAt(now);
            m.setUpdatedAt(now);
            return m;
        });

        CustomerResponseDTO response = useCase.execute(request).getData();

        assertNotNull(response);
        assertEquals(generatedId, response.id());
        assertEquals("João Souza", response.name());
        assertEquals("98765432100", response.cpfCnpj());
        assertEquals("11911112222", response.phone());
        assertEquals("joao@exemplo.com", response.email());
        assertEquals(now, response.createdAt());
        assertEquals(now, response.updatedAt());

        verify(customerRepository).save(argThat(model ->
                request.name().equals(model.getName()) &&
                        request.cpfCnpj().equals(model.getCpfCnpj()) &&
                        request.phone().equals(model.getPhone()) &&
                        request.email().equals(model.getEmail()) &&
                        model.getVehicles() != null && model.getVehicles().isEmpty() &&
                        model.getWorkOrders() != null && model.getWorkOrders().isEmpty()
        ));
    }
}
