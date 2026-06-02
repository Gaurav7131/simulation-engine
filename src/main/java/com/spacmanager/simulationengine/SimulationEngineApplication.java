package com.spacmanager.simulationengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.spacmanager.simulationengine.repository")
public class SimulationEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulationEngineApplication.class, args);
	}
}