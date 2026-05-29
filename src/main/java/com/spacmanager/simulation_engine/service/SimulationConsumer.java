package com.spacmanager.simulation_engine.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SimulationConsumer {

    // This is the "Ear" that listens to the Kafka pipeline
    @KafkaListener(topics = "spac-simulations", groupId = "simulation-group")
    public void consumeSimulationTask(String message) {

        System.out.println("\n🚀 [SIMULATION ENGINE] Received Task from Kafka: " + message);
        System.out.println("⚙️ Crunching heavy mathematical SPAC models...");

        // We simulate a massive, 5-second mathematical calculation here.
        // Because we decoupled this, it will NEVER block the main Core Service!
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Simulation interrupted!");
        }

        System.out.println("✅ [SIMULATION ENGINE] Task Complete! Result saved to database.\n");
    }
}