
# BookMyShow - Movie Ticket Booking Platform

This project is a backend system for a **Movie Ticket Booking Platform**, inspired by the popular **BookMyShow** service. It is built using **Java**, **Spring Boot**, and **MySQL** to provide functionality for managing movies, theatres, showtimes, users, and bookings.

The platform supports features like user authentication, seat selection, booking validation, and admin modules to manage movie catalogs and showtimes. This project follows **clean architecture principles** and integrates industry best practices for scalability, maintainability, and code quality.

## Features

* **RESTful APIs** for:

  * Managing Movies
  * Managing Theatres
  * Managing Showtimes
  * User Management and Authentication
  * Booking Tickets and Viewing Seat Availability
* **User Authentication & Authorization** using **Spring Security** and **JWT Tokens**.
* **Dynamic Seat Selection** with validation and transactional integrity, powered by **JPA** and **Hibernate**.
* **Admin Features** for managing movie data, showtimes, and theatre details with secure endpoints.
* **Centralized Exception Handling** for clean error management and clear API responses.
* **API Testing** with **Postman** to ensure functionality and reliability.
* Built following **Clean Architecture Principles** to ensure modular, maintainable code.

## Technologies Used

* **Backend Framework:** Java, Spring Boot
* **Database:** MySQL
* **Authentication:** Spring Security, JWT Tokens
* **ORM:** JPA (Java Persistence API), Hibernate
* **API Testing:** Postman
* **Build Tool:** Maven

## Prerequisites

Before you start, ensure you have the following installed:

* **Java 11** or later
* **MySQL** database
* **Maven** for building the project

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/RajkumarRavadi/BookMyShow.git
cd BookMyShow
```

### 2. Configure Database

You need a running **MySQL** instance. Create a new database and update the connection details in the `application.properties` file.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookmyshow
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build the Project

Use Maven to build the project:

```bash
mvn clean install
```

### 4. Run the Application

Once the project is built, you can run the Spring Boot application:

```bash
mvn spring-boot:run
```

By default, the application will be available at `http://localhost:8080`.

### 5. API Endpoints

* **Movies**:

  * `GET /api/movies` - Retrieve all movies.
  * `POST /api/movies` - Add a new movie.
* **Theatres**:

  * `GET /api/theatres` - Retrieve all theatres.
  * `POST /api/theatres` - Add a new theatre.
* **Showtimes**:

  * `GET /api/showtimes` - Retrieve all showtimes.
  * `POST /api/showtimes` - Add a new showtime.
* **Bookings**:

  * `GET /api/bookings` - View booking details.
  * `POST /api/bookings` - Book a ticket.
* **Authentication**:

  * `POST /api/auth/login` - Login and get JWT token.
  * `POST /api/auth/register` - Register a new user.

### 6. Admin Features

The admin features require authentication. Once logged in, you can access secure endpoints to manage:

* **Movies**: Add, update, and delete movies.
* **Theatres**: Add, update, and delete theatres.
* **Showtimes**: Define or modify showtimes for different movies.

### Admin Authentication

To authenticate as an admin, first log in via the `POST /api/auth/login` endpoint using your credentials. You'll receive a **JWT token** to be included in the `Authorization` header for admin API calls.

### 7. API Documentation with Swagger (Optional)

For an easy-to-navigate API documentation, you can use **Swagger UI**. Access the Swagger documentation at:

```
http://localhost:8080/swagger-ui.html
```

## Folder Structure

```
BookMyShow
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com
│   │   │   │   └── rajkumar
│   │   │   │       └── bookmyshow
│   │   │   │           ├── controller
│   │   │   │           ├── model
│   │   │   │           ├── repository
│   │   │   │           ├── service
│   │   │   │           ├── security
│   │   │   │           └── exception
│   │   │   └── resources
│   │   │       ├── application.properties
│   │   │       └── static
│   │   └── test
│   │       └── java
│   │           └── com
│   │               └── rajkumar
│   │                   └── bookmyshow
│   │                       └── service
└── pom.xml
```

## Clean Architecture

The project follows **Clean Architecture** principles to ensure a scalable, modular, and maintainable system. The key components are:

* **Controllers**: Handle HTTP requests and responses.
* **Services**: Contain the core business logic.
* **Repositories**: Interface with the database (JPA/Hibernate).
* **Models**: Represent key entities such as Movies, Theatres, Bookings, etc.
* **Security**: Implements authentication and authorization using Spring Security and JWT.
* **Exceptions**: Centralized exception handling for consistent error responses.

## API Testing

You can test the API endpoints using **Postman**. The API collection file is available in the repository for easy import.

1. Open Postman.
2. Click on "Import" in the top left corner.
3. Select the Postman collection file (`BookMyShow.postman_collection.json`).
4. Use the collection to test API endpoints for functionality.


## Acknowledgments

* **Spring Boot** for making backend development efficient and easy.
* **MySQL** for providing a reliable database solution.
* **Postman** for simplifying API testing and development.
* **Swagger** for automatic API documentation.

