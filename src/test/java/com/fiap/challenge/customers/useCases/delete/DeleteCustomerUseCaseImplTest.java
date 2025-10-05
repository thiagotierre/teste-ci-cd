package com.fiap.challenge.customers.useCases.delete;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerDeletionException;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerUseCaseImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private DeleteCustomerUseCaseImpl useCase;

    @Test
    void shouldDeleteWhenCustomerHasNoVehiclesOrWorkOrders() {
        // Arrange
        var id = UUID.randomUUID();
        var customer = new CustomerModel();
        customer.setVehicles(new ArrayList<>());     // lista vazia de VehicleModel
        customer.setWorkOrders(new ArrayList<>());   // lista vazia de WorkOrderModel

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // Act
        useCase.execute(id);

        // Assert
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void shouldThrowWhenCustomerNotFound() {
        // Arrange
        var id = UUID.randomUUID();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(CustomerNotFoundException.class, () -> useCase.execute(id));

        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, never()).delete(any());
    }

    @Test
    void shouldThrowWhenCustomerHasVehiclesOrWorkOrders() {
        // Arrange
        var id = UUID.randomUUID();
        var customer = new CustomerModel();

        // Lista com pelo menos 1 VehicleModel
        var vehicles = new ArrayList<VehicleModel>();
        vehicles.add(new VehicleModel());
        customer.setVehicles(vehicles);

        // Lista vazia de WorkOrderModel (não precisa ter item, já que veículos não está vazia)
        customer.setWorkOrders(new ArrayList<WorkOrderModel>());

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // Act + Assert
        assertThrows(CustomerDeletionException.class, () -> useCase.execute(id));

        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, never()).delete(any());
    }
}
