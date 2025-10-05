package com.fiap.challenge.services.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.challenge.services.entity.ServiceModel;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, UUID>{

	public List<ServiceModel> findAllById(Iterable<UUID> ids);
}
