package com.spacmanager.simulationengine.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacmanager.simulationengine.entity.SimulationResult;
import com.spacmanager.simulationengine.repository.SimulationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j // Production-grade logging
@RequiredArgsConstructor // Automatically creates constructor for injection
public class SimulationConsumer {

    private final SimulationRepository repository;
    private final ObjectMapper objectMapper; // For JSON parsing

    @KafkaListener(topics = "spac-simulations", groupId = "simulation-group")
    public void consumeSimulationTask(String message) {
        log.info("🚀 [SIMULATION ENGINE] Received Task: {}", message);

        try {
            // 1. Parse JSON
            JsonNode jsonNode = objectMapper.readTree(message);
            String ticker = jsonNode.get("ticker").asText();

            // 2. Simulate Heavy Math
            log.info("⚙️ Crunching heavy mathematical models for {}...", ticker);
            Thread.sleep(5000);

            // 3. Create Entity and Save
            SimulationResult result = new SimulationResult();
            result.setTickerSymbol(ticker);
            result.setResultValue(Math.random() * 1000);

            repository.save(result);
            log.info("✅ [SIMULATION ENGINE] Result successfully persisted for {}", ticker);

        } catch (Exception e) {
            log.error("❌ Simulation failed for message: {}", message, e);
        }
    }
}