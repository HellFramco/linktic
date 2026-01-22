# 🏗️ Proyecto: Microservicios con Docker (React + Spring Boot + Kafka + Postgres)

![Docker](https://img.shields.io/badge/Docker-Container-blue)
![Java](https://img.shields.io/badge/Java-17-brightgreen)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![React](https://img.shields.io/badge/React-Vite-blue)
![Kafka](https://img.shields.io/badge/Kafka-7.5.0-orange)
![Postgres](https://img.shields.io/badge/Postgres-15-blue)

---





# Inventory Service

## Introducción
Microservicio responsable de la gestión de inventario y del flujo de compra.
Forma parte de una arquitectura de microservicios junto con `products`.

## Arquitectura
- Spring Boot 3
- REST síncrono
- Base de datos por microservicio
- Comunicación HTTP entre servicios

## Decisiones técnicas
- Transactional Script para el flujo de compra
- Anti-Corruption Layer para integración con Product Service
- API Key simple entre microservicios
- JSON:API como contrato de respuesta
- Retry + timeout para resiliencia básica

## Modelo
Inventory:
- productId
- quantity
- state

## Endpoints
- GET /inventory/{productId}
- PUT /inventory/{productId}
- POST /purchases

## Flujo de compra
1. Inventory recibe request
2. Llama a Product Service
3. Verifica existencia
4. Verifica stock
5. Descuenta inventario (transacción)
6. Retorna compra
7. Registra historial (futuro)

## Seguridad
- Header: X-API-KEY
- Validación vía OncePerRequestFilter
- Keys configuradas por variables de entorno

## Manejo de errores
- 404: Producto inexistente
- 409: Inventario insuficiente
- 504: Timeout Product Service

## Testing
- Unitarios: servicios con mocks
- Integración: @SpringBootTest + H2
- Un test real por microservicio

## Git Flow
- main
- develop
- feature/product
- feature/inventory
- feature/purchase

## Uso de IA
ChatGPT fue utilizado como apoyo para:
- Definición de arquitectura
- Estructura del proyecto
- Buenas prácticas y patrones

## Mejoras futuras
- Event-driven con Kafka
- Saga Pattern
- Circuit Breaker (Resilience4j)
- Cache de productos (Redis)
- Observabilidad (Prometheus + Grafana)
