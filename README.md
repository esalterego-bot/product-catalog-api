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

## NOTES
Interview answer

If somebody asks:

Why does ProductRepository extend JpaRepository<Product, Long>?

A good answer is:

JpaRepository provides CRUD operations and query capabilities for a specific entity type. Product indicates the entity being managed and Long indicates the type of its primary key.

That's exactly the level I'd expect from a Spring Boot developer with around 1-2 years of experience.


Notice something important:

Controller
↓
DTO
↓
Service
↓
Entity
↓
Repository

The Repository deals with Entities.

The Controller deals with DTOs.

The Service often sits in the middle and converts between them.

Responses usually don't need:

@NotBlank
@NotNull
@Positive

because we're not validating user input anymore.

When a request arrives:

{
"name": "Coca Cola",
"category": "DRINKS",
"price": 1.50,
"stock": 100
}

How do we transform:

CreateProductRequest

into:

Product

My interview answer(For mapping the request)

If asked:

Why not MapStruct?

I'd say:

For a small take-home assignment I prefer a simple custom mapper. It keeps dependencies minimal while still separating mapping logic from the service layer.

That's a mature answer.


This won't work:

Product.name = request.name;

because:

name is not a static field.
DTO fields are usually private.
You need a Product instance first.

Think step by step:


This is actually a very interview-friendly answer:

For a basic CRUD API I would reject duplicates with a 409 Conflict.
Inventory updates should be handled through dedicated update operations rather than product creation.

@RestControllerAdvice
public class GlobalExceptionHandler {

}
What does it do?

Think:

ProductService
↓
throws ProductAlreadyExistsException
↓
GlobalExceptionHandler catches it
↓
Returns HTTP 409

Without it:

Exception
↓
500 Internal Server Error


@RequestBody

means:

Take the JSON body
↓
Convert it into CreateProductRequest

@Valid

means:

Check:
@NotBlank
@NotNull
@Positive
@PositiveOrZero


At this point, your API flow is basically complete:

POST /products
↓
ProductController
↓
ProductServiceImpl
↓
Duplicate check
↓
ProductMapper.toEntity()
↓
ProductRepository.save()
↓
ProductMapper.toResponse()
↓
ProductResponse