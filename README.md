# Spring Boot Microservices Template

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-green)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-blue)
![Kafka](https://img.shields.io/badge/Kafka-7.5.0-black)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)

A complete microservices ecosystem with event-driven architecture. Includes service discovery, 
centralized config, API gateway, async Kafka messaging, circuit breaking, and database integration — 
all containerized and ready to clone and extend.

⭐ **If you find this project helpful, please consider giving it a star!** ⭐

## Architecture Overview

This template implements a complete microservices ecosystem with event-driven communication:

```sh
┌─────────────┐
│   Client    │
└──────┬──────┘
       │
       ▼
┌─────────────────────────────────────┐
│      API Gateway (Port 8080)        │
│   - Request Routing                 │
│   - Load Balancing                  │
└──────┬──────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────┐
│  Eureka Server (Port 8761)          │
│   - Service Discovery               │
│   - Service Registry                │
└──────┬──────────────────────────────┘
       │
       ├──────────────────┬─────────────────┬──────────────────┐
       ▼                  ▼                 ▼                  ▼
┌─────────────┐   ┌──────────────┐  ┌──────────────┐  ┌─────────────┐
│Config Server│   │ User Service │  │ Notification │  │   Future    │
│ (Port 8888) │   │ (Port 8081)  │  │   Service    │  │  Services   │
└─────────────┘   └──────┬───────┘  │ (Port 8082)  │  └─────────────┘
                         │          └──────▲───────┘
                         │                 │
                         ▼                 │
                  ┌──────────────┐         │
                  │  PostgreSQL  │         │
                  │ (Port 2500)  │         │
                  └──────────────┘         │
                         │                 │
                         └─────────────────┘
                                   │
                         ┌─────────▼─────────┐
                         │  Kafka + Zookeeper│
                         │  (Ports 9092/2181)│
                         │  Event Streaming  │
                         └───────────────────┘
```

## Components

### 1. **Eureka Server** (Service Discovery)

- **Port:** 8761
- **Purpose:** Service registry for dynamic service discovery
- **Dashboard:** <http://localhost:8761>

### 2. **API Gateway**

- **Port:** 8080
- **Purpose:** Single entry point for all client requests
- **Features:**
  - Routing to microservices
  - Load balancing
  - Eureka integration

### 3. **Config Server**

- **Port:** 8888
- **Purpose:** Centralized configuration management
- **Features:** Externalized configuration for all services

### 4. **User Service** (Example Microservice)

- **Port:** 8081
- **Purpose:** Demonstrates a complete microservice with database integration
- **Features:**
  - RESTful API endpoints
  - PostgreSQL database integration
  - JPA/Hibernate ORM
  - Kafka event producer (publishes user events)
  - OpenFeign client for inter-service communication
  - Resilience4j for circuit breaker pattern
  - Spring Boot Actuator for monitoring

### 5. **Mail Service** (Event Consumer)

- **Port:** 8082
- **Purpose:** Demonstrates event-driven microservice architecture
- **Features:**
  - Kafka event consumer (listens to user events)
  - Processes USER_CREATED, USER_UPDATED, USER_DELETED events
  - Asynchronous event processing
  - Decoupled from User Service

### 6. **Apache Kafka** (Event Streaming Platform)

- **Port:** 9092 (external), 29092 (internal)
- **Purpose:** Message broker for asynchronous communication between services
- **Features:**
  - Event streaming and pub/sub messaging
  - Decouples microservices
  - Enables event-driven architecture
  - Auto-creates topics on demand

### 7. **Apache Zookeeper**

- **Port:** 2181
- **Purpose:** Coordination service for Kafka
- **Features:**
  - Manages Kafka cluster metadata
  - Handles leader election
  - Tracks broker membership

### 8. **PostgreSQL Database**

- **Port:** 2500 (host) → 5432 (container)
- **Database:** userdb
- **Credentials:** postgres/postgres

## Quick Start

### Prerequisites

- **Java 21** or higher
- **Maven 3.9+**
- **Docker** and **Docker Compose**
- **Git**

### Running the Application

1. **Clone the repository**

   ```bash
   git clone https://github.com/Goal651/springboot_microservice
   cd SPRINGBOOT_MICROSERVICE
   ```

2. **Start all services with Docker Compose**

   ```bash
   docker-compose up --build
   ```

   This command will:
   - Build all Docker images
   - Start Zookeeper (Kafka coordinator)
   - Start Kafka broker
   - Start PostgreSQL database
   - Start Eureka Server
   - Start Config Server
   - Start API Gateway
   - Start User Service (Kafka producer)
   - Start Notification Service (Kafka consumer)

3. **Wait for services to start** (approximately 3-4 minutes)
   - Monitor logs: `docker-compose logs -f`
   - Check Eureka Dashboard: <http://localhost:8761>
   - Kafka takes ~30 seconds to be ready

4. **Verify services are registered**
   - Open <http://localhost:8761>
   - You should see `USER-SERVICE`, `MAIL-SERVICE`, `API-GATEWAY`, and `CONFIG-SERVER` registered

### Testing the Application

**Access User Service through API Gateway:**

```bash
curl http://localhost:8080/users/1
```

**Direct access to User Service:**

```bash
curl http://localhost:8081/users/1
```

**Check service health:**

```bash
curl http://localhost:8081/actuator/health
```

**Test Kafka Event Flow:**

```bash
# Create a user (triggers Kafka event)
curl http://localhost:8081/users/1

# Check mail-service logs to see event consumed
docker-compose logs -f mail-service

# You should see:
# "Received user event: UserEvent(...)"
# "Processing USER_CREATED event for user: John Doe"
```

## 🛠️ Technology Stack

### Core Framework

- **Spring Boot:** 3.5.7
- **Spring Cloud:** 2025.0.0
- **Java:** 21

### Spring Cloud Components

- **Netflix Eureka:** Service discovery
- **Spring Cloud Gateway:** API gateway
- **Spring Cloud Config:** Centralized configuration
- **OpenFeign:** Declarative REST client
- **Resilience4j:** Circuit breaker, retry, rate limiter

### Database & Persistence

- **PostgreSQL:** 15
- **Spring Data JPA:** Data access layer
- **Hibernate:** ORM framework

### Event Streaming

- **Apache Kafka:** 7.5.0 (Confluent Platform)
- **Apache Zookeeper:** 7.5.0 (Confluent Platform)
- **Spring Kafka:** Event-driven messaging

### Additional Libraries

- **Lombok:** Reduce boilerplate code
- **Spring Boot Actuator:** Production monitoring
- **Spring Boot DevTools:** Development productivity

### Containerization

- **Docker:** Containerization
- **Docker Compose:** Multi-container orchestration

## 📁 Project Structure

```sh
SPRINGBOOT_MICROSERVICE/
├── eureka/                    # Service Discovery Server
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── gateway/                   # API Gateway
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── configServer/              # Config Server
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── userService/               # User Microservice (Kafka Producer)
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/tutorial/userService/
│   │       │       ├── controller/      # REST controllers
│   │       │       ├── model/           # JPA entities
│   │       │       ├── repositories/    # Data repositories
│   │       │       ├── services/        # Business logic
│   │       │       ├── producer/        # Kafka producers
│   │       │       ├── dto/             # Data transfer objects
│   │       │       ├── config/          # Kafka configuration
│   │       │       └── client/          # Feign clients
│   │       └── resources/
│   │           └── application.properties
│   ├── Dockerfile
│   └── pom.xml
├── notificationService/       # Notification Service (Kafka Consumer)
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/tutorial/notificationService/
│   │       │       ├── consumer/        # Kafka consumers
│   │       │       └── dto/             # Data transfer objects
│   │       └── resources/
│   │           └── application.properties
│   ├── Dockerfile
│   └── pom.xml
└── docker-compose.yml         # Docker orchestration (includes Kafka & Zookeeper)
```

## Event-Driven Architecture with Kafka

This template demonstrates asynchronous, event-driven communication between microservices using Apache Kafka.
