# GamePass App

This is a Spring Boot application that clones the XBOX GamePass platform as a practice.  



## Table of Contents

- [Features](#features)
- [Installation](#installation)
    - [Prerequisites](#prerequisites)
    - [Installation Steps](#installation-steps)
- [Project Steps](#project-steps)
- [Sample Environment Variables](#sample-environment-variables)
- [License](#license)



## Features

- JWT Authentication
- Exception Handling
- Validation
- Swagger OpenAPI
- Subscription, Payment and Invoice
- Integration Testing
- TestContainers
- Scheduling
- Docker Image



## Installation

### Prerequisites
- Docker

### Installation Steps
- `git clone https://gitlab.com/onurhizar/spring-boot-gamepass-app`
- `cd spring-boot-gamepass-app`
- `./mvnw clean package -DskipTests` to build JAR file
- `docker compose up` to run app
- `http://localhost:8080/swagger-ui.html` to see Swagger UI of API routes
- `docker compose down` to stop the app and remove the images



## Project Steps
- [x] Game entity and database connection
- [x] Populate sample data with data.sql
- [x] Hierarchical Categories for Game entity
- [x] Exception Handling (initialized)
- [x] ManyToMany Bidirectional relationship on Category-Game
- [x] DTO for bidirectional entities
- [x] CRUD on Category with DTO
- [x] User entity
- [x] Auth (JWT)
- [x] OpenAPI & Swagger
- [x] JWT environment variable constants 
- [x] Validation
- [x] Integration Testing (initialized)
- [x] User Interests : Favorite / Unfavorite Games
- [x] User Verification & Password Reset
- [x] Admin or Self Authorization Logic
- [x] Scheduling
- [x] Subscription and ContractRecord
- [x] Invoice and Payment
- [x] TestContainers
- [x] CommonEntity for createdAt and updatedAt
- [x] Dockerfile
- [x] Other entities
- [ ] Business Logic
- [x] Downgrade from Java 17 to Java 8
- [ ] Review and Refactoring



## Sample Environment Variables

```text
DB_URL=jdbc:postgresql://localhost:5432/gamepass
DB_USERNAME=postgres
DB_PASSWORD=secretpassword
JWT_ISSUER=GamePass
JWT_EXPIRATION_HOURS=12
JWT_SECRET_KEY=104E635266556A586E3272367538722F413F4438472B4B6250645367266B5275
UPLOAD_DIRECTORY=/tmp
```



## License

This application is licensed under the MIT License. See the LICENSE file for more information.
