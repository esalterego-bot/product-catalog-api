# Product Catalog API

A RESTful API built with Spring Boot for managing products in a catalog.

## Features

* Create products
* Retrieve all products
* Retrieve a product by ID
* Update products
* Delete products
* Input validation
* Global exception handling
* PostgreSQL database
* Dockerized database
* Swagger/OpenAPI documentation
* Spring Security with Basic Authentication

## Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Security
* PostgreSQL 17
* Docker & Docker Compose
* Swagger / OpenAPI
* Maven
* Lombok

## Project Structure

```text
controller      REST endpoints
service         Business logic
repository      Data access layer
entity          JPA entities
dto             Request and response objects
mapper          Entity/DTO mapping
exception       Custom exceptions and handlers
configuration   Application configuration
```

## Running the Application

### 1. Start PostgreSQL

```bash
docker compose up -d
```

### 2. Run the application

```bash
./mvnw spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

## Authentication

The API uses HTTP Basic Authentication.

Example:

```bash
curl -u javier:admin123 http://localhost:8080/products
```

## Available Endpoints

| Method | Endpoint       | Description              |
| ------ | -------------- | ------------------------ |
| POST   | /products      | Create a product         |
| GET    | /products      | Retrieve all products    |
| GET    | /products/{id} | Retrieve a product by ID |
| PUT    | /products/{id} | Update a product         |
| DELETE | /products/{id} | Delete a product         |

## Example Request

```json
{
  "name": "Coca-Cola",
  "category": "DRINKS",
  "price": 10.00,
  "stock": 10
}
```

## Future Improvements

* Unit and integration tests
* JWT authentication
* Pagination and filtering
* CI/CD pipeline
* Deployment to AWS

```
```

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