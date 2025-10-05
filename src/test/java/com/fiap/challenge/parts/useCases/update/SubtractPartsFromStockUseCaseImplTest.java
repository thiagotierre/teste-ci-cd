package com.fiap.challenge.parts.useCases.update;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.customers.useCases.find.FindCustomerByIdUseCaseImpl;
import com.fiap.challenge.shared.model.ResponseApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
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
    private CustomerModel mockCustomer;

    @BeforeEach
    void setUp() {

        customerId = UUID.randomUUID();
        mockCustomer = new CustomerModel();
        mockCustomer.setId(customerId);
        mockCustomer.setName("John Doe");
        mockCustomer.setCpfCnpj("12345678901");
        mockCustomer.setPhone("999999999");
        mockCustomer.setEmail("john@example.com");
        mockCustomer.setCreatedAt(OffsetDateTime.now().withNano(0));
        mockCustomer.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    void shouldReturnCustomerWhenFound() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        ResponseApi<CustomerResponseDTO> response = useCase.execute(customerId);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("Customer found successfully.");
        assertThat(response.getData())
                .extracting(CustomerResponseDTO::id)
                .isEqualTo(customerId);
    }

    @Test
    void shouldReturnNotFoundWhenCustomerDoesNotExist() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        ResponseApi<CustomerResponseDTO> response = useCase.execute(customerId);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).contains("Customer not found with ID");
        assertThat(response.getData()).isNull();
    }
}