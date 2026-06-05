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