package com.fiap.challenge.customers.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiap.challenge.customers.entity.CustomerModel;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

    public boolean existsByCpfCnpj(String cpfCnpj);

    public Optional<CustomerModel> findByCpfCnpj(String cpfCnpj);

    public Optional<CustomerModel> findByCpfCnpjAndIdNot(String cpfCnpj, UUID id);

    @Query(value = "SELECT c FROM Customer c WHERE c.cpfCnpj LIKE %:cpfCnpj% " +
            "ORDER BY " +
            "  CASE WHEN c.cpfCnpj LIKE :cpfCnpj% THEN 0 ELSE 1 END, " +
            "  c.cpfCnpj ASC", nativeQuery = true)
     public List<CustomerModel> findByCpfCnpjContainingWithCustomSort(@Param("cpfCnpj") String cpfCnpj);
}