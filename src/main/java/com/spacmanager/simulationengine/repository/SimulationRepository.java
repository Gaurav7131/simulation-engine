package com.spacmanager.simulationengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spacmanager.simulationengine.entity.SimulationResult;

@Repository
public interface SimulationRepository extends JpaRepository<SimulationResult, Long> {
    // Spring Data JPA provides the CRUD methods automatically
}