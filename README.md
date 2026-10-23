# Product Catalog API

REST API built with Spring Boot for managing products in a product catalog.

## Features

* Create products
* Retrieve all products
* Retrieve a product by ID
* Update existing products
* Delete products
* Input validation using Jakarta Validation
* Global exception handling
* PostgreSQL persistence
* Dockerized database
* Swagger/OpenAPI documentation
* Spring Security authentication
* Unit testing with JUnit and Mockito

## Tech Stack

* Java 21
* Spring Boot 3
* Spring Data JPA
* Spring Security
* PostgreSQL 17
* Docker & Docker Compose
* Swagger / OpenAPI
* JUnit 5
* Mockito
* Lombok
* Maven

## Architecture

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

### Layers

| Layer         | Responsibility                   |
| ------------- | -------------------------------- |
| Controller    | HTTP request handling            |
| Service       | Business logic                   |
| Repository    | Database access                  |
| DTO           | Request/Response models          |
| Mapper        | Entity ↔ DTO conversion          |
| Exception     | Error handling                   |
| Configuration | Swagger & Security configuration |

## Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.mercadona.product_catalog
│   │       ├── configuration
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── enums
│   │       ├── exception
│   │       ├── mapper
│   │       ├── repository
│   │       └── service
│   └── resources
│       └── application.properties
└── test
    └── java
```

## Running the Application

### Start PostgreSQL

```bash
docker compose up -d
```

Verify container:

```bash
docker ps
```

### Run the Application

```bash
./mvnw spring-boot:run
```

Application URL:

```text
http://localhost:8080
```

## Authentication

The API is protected using Spring Security Basic Authentication.

Example:

```bash
curl -u javier:admin123 http://localhost:8080/products
```

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

## Available Endpoints

| Method | Endpoint       | Description            |
| ------ | -------------- | ---------------------- |
| POST   | /products      | Create product         |
| GET    | /products      | Retrieve all products  |
| GET    | /products/{id} | Retrieve product by ID |
| PUT    | /products/{id} | Update product         |
| DELETE | /products/{id} | Delete product         |

## Example Request

```json
{
  "name": "Coca-Cola",
  "category": "DRINKS",
  "price": 10.00,
  "stock": 10
}
```

## Running Tests

```bash
./mvnw test
```

## Future Improvements

* JWT authentication
* Integration tests
* Pagination and filtering
* AWS deployment
* CI/CD pipeline
* Monitoring with Datadog

```
```
## Run jenkins
docker run -p 8080:8080 -p 50000:50000 jenkins/jenkins:lts

admin
5c944b37c42744f28a031485bb0ca013