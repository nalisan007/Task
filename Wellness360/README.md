# Task Management REST API

A production-style Task Management REST API built using Java 21 and Spring Boot.

This project was developed as part of a backend case study to demonstrate REST API design, authentication, validation, testing, exception handling, and clean architecture practices.
  
> [!Tip]
> Please Read documentation for detailed information.


> [!Important] 
> Please download and import Postman Collections file for testing 

# Features
  - User Registration & Login
  - JWT Authentication
  - Create, Read, Update, Delete Tasks
  - Pagination & Sorting
  - Input Validation
  - Global Exception Handling
  - API Versioning
  - Opimistic Locking
  - Unit Testing & Integration Testing
  - Swagger/OpenAPI Documentation
  - Metric and observability using Spring Actuator

# Techstack used  
Tech Stack  
Technology	    - Purpose  
`Java 21`	        Core Language  
`Spring Boot`	   - Backend Framework  
`Spring Web MVC` - REST API building  
`Spring Security`-	Authentication & Authorization  
`JWT`        	   - Stateless Authentication  
`Spring Data JPA`-	Database Access  
`Spring Actuator`- Metrics for observability  
`Hibernate`	     - ORM and Data Validation using Hibernate validator  
`H2 Database`	   - In-memory Database  
`JUnit 5`	       - Unit Testing  
`Mockito`	       - Mocking Framework for Integration testing  
`Swagger/OpenAPI`-	API Documentation using OpenAPI v3 spec  
`Postman`        - Collections file uploaded for testing endpoint  
# Important API Endpoint  
<img width="1832" height="859" alt="auth api" src="https://github.com/user-attachments/assets/90406bf5-57b5-4804-a49e-3e31d4f7119b" />



    
<img width="451" height="277" alt="task api" src="https://github.com/user-attachments/assets/8fe44682-dcdf-46c6-9827-f9e03de9326a" />

# Authentication
  
This project uses JWT-based authentication.  
  
After login, include the token in request headers:  
  
> Authorization: Bearer <token>  

Implemented Security Features:  
  
+ JWT Authentication  
+ BCrypt Password Hashing  
+ Stateless Session Management  
+ Token Blacklisting for Logout  
+ Token Expiration  

# Running the Application  
 ### Prerequisites  
Java 21+  
Maven    
  
### Clone Repository  
>git clone < repository-url >  
>cd < project-folder >  
### Run Application
>mvn spring-boot:run  
### Run Integration & Unit Tests  
>mvn test  
### API Documentation

#### Swagger UI:  

>http://localhost:8080/swagger-ui/index.html  

#### OpenAPI Docs:  

>http://localhost:8080/v3/api-docs

#### Postman Collection File: 

>[Postman Collection JSON File]()

