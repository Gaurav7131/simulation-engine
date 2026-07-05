SPAC Financial Simulation Engine 🚀
The Simulation Engine is a high-performance, asynchronous worker microservice designed to handle compute-intensive mathematical modeling for the SPAC (Special Purpose Acquisition Company) ecosystem.

By decoupling heavy computational tasks (e.g., public shareholder dilution scenarios, PIPE investor returns) from the primary transactional core service, this engine guarantees high availability, fault tolerance, and non-blocking API performance using an event-driven architecture.

🏗️ Architecture Overview
In the larger distributed ecosystem, the Simulation Engine acts as the "Worker".

Listens: It continuously polls a dedicated Apache Kafka topic (spac-simulations) securely connected via the cloud (Aiven/Confluent).

Computes: Upon receiving a SimulationRequest payload, it deserializes the data and performs heavy mathematical crunching.

Persists: Once the calculation is complete, it maps the results to a SimulationResult entity and persists it directly into a PostgreSQL database using Spring Data JPA.

💻 Tech Stack
Language: Java 21 (LTS)

Framework: Spring Boot 3.x

Build Tool: Maven

Asynchronous Messaging: Apache Kafka (Spring for Apache Kafka)

Database: PostgreSQL

ORM: Spring Data JPA / Hibernate

Boilerplate Reduction: Lombok

JSON Processing: Jackson (ObjectMapper)

📋 Prerequisites
Before running this microservice, ensure you have the following installed and running:

Java 21 JDK installed and configured in your JAVA_HOME.

PostgreSQL running locally or in the cloud with a database named spac_db.

Apache Kafka cluster (Local or Managed Cloud like Aiven/Confluent).

⚙️ Configuration & Secrets
The service relies on src/main/resources/application.yml. You must configure your database and secure Kafka cloud credentials before booting.

YAML
server:
port: 8082 # Explicitly set to avoid colliding with Gateway/Core Service

spring:
application:
name: simulation-engine

datasource:
url: jdbc:postgresql://localhost:5432/spac_db
username: <YOUR_DB_USERNAME>
password: <YOUR_DB_PASSWORD>
driver-class-name: org.postgresql.Driver

jpa:
hibernate:
ddl-auto: update
show-sql: true

kafka(Go to Aiven console managed kafka infrastructure):
bootstrap-servers: <YOUR_KAFKA_BOOTSTRAP_SERVER>
properties:
security.protocol: SASL_SSL
sasl.mechanism: PLAIN
sasl.jaas.config: org.apache.kafka.common.security.plain.PlainLoginModule required username="<YOUR_API_KEY>" password="<YOUR_API_SECRET>";
consumer:
group-id: simulation-group
auto-offset-reset: earliest
key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
value-deserializer: org.apache.kafka.common.serialization.StringDeserializer

Setup and Launch

1. Clone/Navigate to the Directory
   Ensure you are at the root of the simulation-engine module.

2. Clean and Install Dependencies
   Resolve your Maven dependencies and ensure there are no compilation errors:

Bash
./mvnw clean install -DskipTests 3. Run the Microservice
Launch the Spring Boot application:

Bash
./mvnw spring-boot:run
Upon successful boot, you will see two critical logs:

Tomcat initialized with port(s): 8082

simulation-group: partitions assigned: [spac-simulations-0] (Confirming successful Kafka subscription).

📂 Project Structure
A clean, domain-driven structure mapping to standard enterprise patterns:

Plaintext
src/main/java/com/spacmanager/simulationengine/
├── SimulationEngineApplication.java # Main Boot Class
├── consumer/ # Event-driven listeners
│ └── SimulationConsumer.java # @KafkaListener bridging Kafka to Business Logic
├── dto/ # Data Transfer Objects
│ └── SimulationRequest.java # Matches Core Service JSON payload
├── entity/ # JPA Database Models
│ └── SimulationResult.java # Maps to 'simulation_results' Postgres table
└── repository/ # Database interfaces
└── SimulationRepository.java # Extends JpaRepository
📡 Kafka Messaging Contract
Topic: spac-simulations
Consumer Group: simulation-group

Incoming Message (DTO)
The engine expects a JSON payload from the Core Service structured via the SimulationRequest DTO. Note: Ensure camelCase consistency during deserialization (e.g., targetId instead of target_id).

🛑 Troubleshooting
Connection refused / SASL authentication failed: Double-check the Kafka credentials (API_KEY and SECRET) and ensure proper indentation in application.yml.

UnrecognizedPropertyException: The incoming JSON keys from the producer (Core Service) do not strictly match your SimulationRequest.java property names.

Port 8080 already in use: The Simulation Engine defaults to 8082. Ensure server.port=8082 is actively being read by Spring Boot.
