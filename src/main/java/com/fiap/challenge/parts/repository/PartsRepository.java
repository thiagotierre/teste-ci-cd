package com.fiap.challenge.parts.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.challenge.parts.entity.PartModel;

@Repository
public interface PartsRepository extends JpaRepository<PartModel, UUID>{

}
