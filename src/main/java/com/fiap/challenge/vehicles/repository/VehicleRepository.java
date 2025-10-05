package com.fiap.challenge.vehicles.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.challenge.vehicles.entity.VehicleModel;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleModel, UUID> {

	public List<VehicleModel> findAllById(Iterable<UUID> ids);

	public Optional<VehicleModel> findByLicensePlateIgnoreCase(String licensePlate);

	public List<VehicleModel> findByCustomerCpfCnpj(String cpfCnpj);
}
