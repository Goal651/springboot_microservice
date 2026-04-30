# Advanced Spring Boot Microservices Template

A production-ready, highly-scalable microservice template built with Spring Boot 3.x, designed for high performance, resilience, and operational excellence.

---

## System Architecture

```
                     +------------------+
                     |   Client Apps    |
                     +--------+---------+
                              |
                              v
                     +------------------+
                     |  API Gateway     |
                     |      :8080       |
                     +--------+---------+
                              |
            +-----------------+-----------------+
            |                 |                 |
            v                 v                 v
    +-----------+     +-----------+     +-----------+
    | Auth      |     | User      |     | Mail      |
    | Service   |     | Service   |     | Service   |
    |   :8083   |     |   :8081   |     |   :8084   |
    +-----+-----+     +-----+-----+     +-----+-----+
          |                 |                 |
          |                 |                 |
          |    +------------+------------+     |
          |    |                         |     |
          |    v                         v     |
          |  +-----+                 +-----+   |
          |  |Redis|                 |Kafka|   |
          |  |:6379|                 |:9092|   |
          |  +-----+                 +--+--+   |
          |    |                        |      |
          |    |                        |      |
          v    v                        v      v
    +-------------------------------------------------+
    |              PostgreSQL :5432                   |
    +-------------------------------------------------+

    Service Discovery & Config:
    +----------------+      +----------------+
    | Eureka Server  |      | Config Server  |
    |     :8761      |      |     :8888      |
    +----------------+      +----------------+

    All services register with Eureka and fetch config from Config Server.
```

---

## Technology Stack

| Category | Technology |
| :--- | :--- |
| **Core** | Java 21, Spring Boot 3.5.x, Spring Cloud 2025 |
| **Persistence** | PostgreSQL, Spring Data JPA |
| **Caching** | Redis (with JSON Serialization) |
| **Messaging** | Apache Kafka |
| **Discovery** | Netflix Eureka |
| **Gateway** | Spring Cloud Gateway |
| **Configuration** | Spring Cloud Config Server |
| **Resilience** | Resilience4j, Spring Retry |
| **Deployment** | Docker Compose, Kubernetes (StatefulSets & Deployments) |

---

## Core Workflows

### 1. Authentication & Security

```
    Client           API Gateway        Auth Service       User Service
      |                   |                  |                  |
      | POST /auth/login  |                  |                  |
      |------------------>|                  |                  |
      |                   | Forward Request   |                  |
      |                   |------------------>|                  |
      |                   | Validate Creds    |                  |
      |                   |                  |----------------->|
      |                   |                  |                  |
      |                   |                  | User Details     |
      |                   |                  |<-----------------|
      |                   |                  |                  |
      |                   | JWT Token        |                  |
      |                   |<------------------|                  |
      |                   |                  |                  |
      | 200 OK (JWT)      |                  |                  |
      |<------------------|                  |                  |
```

### 2. Event-Driven Communication (Kafka)

```
    User Service            Kafka Topic            Mail Service           SMTP Server
         |                      |                      |                      |
         | Publish USER_CREATED  |                      |                      |
         |--------------------->|                      |                      |
         |                      | Consume Event        |                      |
         |                      |--------------------->|                      |
         |                      |                      | Send Welcome Email   |
         |                      |                      |--------------------->|
```

### 3. Distributed Caching (Redis)

```
                    Get User By ID
                          |
                          v
                 +----------------+
                 | Redis Cache?   |
                 +-------+--------+
                         |
           +-------------+-------------+
           |                           |
           v                           v
        (Hit)                       (Miss)
           |                           |
           v                           v
    Return User              Query PostgreSQL
                                |
                                v
                         Save to Cache
                                |
                                v
                         Return User
```

---

## Kubernetes Deployment

We provide a full-scale Kubernetes orchestration setup in the `k8s/` directory.

```bash
# 1. Create Namespace
kubectl apply -f k8s/namespace.yaml

# 2. Setup Config & Secrets
kubectl apply -f k8s/app-config.yaml
kubectl apply -f k8s/app-secrets.yaml

# 3. Deploy Infrastructure
kubectl apply -f k8s/infrastructure/ --recursive

# 4. Deploy Services
kubectl apply -f k8s/config-server/
kubectl apply -f k8s/user-service/
kubectl apply -f k8s/auth-service/
kubectl apply -f k8s/mail-service/
kubectl apply -f k8s/gateway/
```

---

## Project Structure

```text
.
├── authService/        # JWT & Authentication Logic
├── userService/        # User Management & Redis Caching
├── mailService/        # Kafka Consumer for Notifications
├── gateway/            # Spring Cloud Gateway
├── eureka/             # Service Discovery
├── configServer/       # Centralized Configuration
├── k8s/                # Kubernetes Manifests
└── compose.yml         # Local Development Orchestration
```

---

## Quick Start

### Docker Development
```bash
# Start everything
docker compose up -d

# Check health
curl http://localhost:8080/actuator/health
```

### Monitoring
- **Eureka Dashboard**: <http://localhost:8761>
- **Config Server**: <http://localhost:8888/user-service/default>
- **Redis Stats**: `docker exec -it redis redis-cli info`

---

## Design & Performance
- **Optimized Resource Limits**: Tailored CPU/Memory limits for each service.
- **Circuit Breakers**: Implemented via Resilience4j to prevent cascading failures.
- **JSON Caching**: Redis values are stored as JSON for cross-service readability.
- **Stateful Infrastructure**: K8s deployments use `StatefulSet` for DB and Kafka stability.
