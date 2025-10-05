package com.fiap.challenge.customers.useCases.find;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.model.ResponseApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCustomerByIdUseCaseImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private FindCustomerByIdUseCaseImpl useCase;

    private UUID customerId;
    private CustomerModel customer;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();

        customer = new CustomerModel();
        customer.setId(customerId);
        customer.setName("John Doe");
        customer.setCpfCnpj("12345678901");
        customer.setPhone("999999999");
        customer.setEmail("john@example.com");
        customer.setCreatedAt(OffsetDateTime.now().minusDays(5));
        customer.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    void shouldReturnCustomerWhenIdExists() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        ResponseApi<CustomerResponseDTO> response = useCase.execute(customerId);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("Customer found successfully.");
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().id()).isEqualTo(customerId);
        assertThat(response.getData().name()).isEqualTo("John Doe");
    }

    @Test
    void shouldReturnNotFoundWhenIdDoesNotExist() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        ResponseApi<CustomerResponseDTO> response = useCase.execute(customerId);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).isEqualTo("Customer not found with ID: " + customerId);
        assertThat(response.getData()).isNull();
    }
}