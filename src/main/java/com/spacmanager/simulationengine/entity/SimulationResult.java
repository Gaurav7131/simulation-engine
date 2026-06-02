package com.spacmanager.simulationengine.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "simulation_results")
@Data
public class SimulationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tickerSymbol;
    private Double resultValue;
    private LocalDateTime executionTime;

    @PrePersist
    protected void onCreate() {
        executionTime = LocalDateTime.now();
    }
}