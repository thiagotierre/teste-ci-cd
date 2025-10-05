package com.fiap.gateway.customer;

import com.fiap.application.gateway.customer.CustomerGateway;
import com.fiap.core.domain.customer.Customer;
import com.fiap.mapper.customer.CustomerMapper;
import com.fiap.persistence.entity.customer.CustomerEntity;
import com.fiap.persistence.repository.customer.CustomerEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerRepositoryGateway implements CustomerGateway {
    private final CustomerEntityRepository customerEntityRepository;
    private final CustomerMapper customerMapper;

    public CustomerRepositoryGateway(CustomerEntityRepository customerEntityRepository, CustomerMapper customerMapper) {
        this.customerEntityRepository = customerEntityRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer create(Customer customer) {
        CustomerEntity customerEntity = customerEntityRepository.save(customerMapper.toEntity(customer));
        return customerMapper.toDomain(customerEntity);
    }

    @Override
    public Customer update(Customer customer) {
        CustomerEntity customerEntity = customerEntityRepository.save(customerMapper.toEntity(customer));
        return customerMapper.toDomain(customerEntity);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        Optional<CustomerEntity> customerEntity = customerEntityRepository.findById(customerId);

        return customerEntity.map(customerMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByDocumentNumber(String documentNumber) {
        Optional<CustomerEntity> customerEntity = customerEntityRepository.findByDocumentNumber(documentNumber);

        return customerEntity.map(customerMapper::toDomain);
    }

    @Override
    public void delete(Customer customer) {
        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        customerEntityRepository.delete(customerEntity);
    }
}
