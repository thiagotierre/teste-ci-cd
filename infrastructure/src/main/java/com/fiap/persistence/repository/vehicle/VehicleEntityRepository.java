package com.fiap.persistence.repository.vehicle;

import com.fiap.persistence.entity.vehicle.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, UUID> {

    Optional<VehicleEntity> findByLicensePlateIgnoreCase(String licensePlate);
}