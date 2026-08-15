# 🚀 Code Review Platform Backend

A secure and scalable backend for a **Code Review Platform**, built using **Spring Boot**, **Spring Security**, **JWT Authentication**, **PostgreSQL**, and **Docker**.

The project follows a layered architecture and RESTful API design, providing a strong foundation for managing users, code submissions, reviews, and collaboration features.

---

## ✨ Features

### Authentication

* User Registration
* User Login
* JWT-based Authentication
* BCrypt Password Encryption
* Protected REST APIs

### Validation & Error Handling

* Request Validation using Bean Validation
* Global Exception Handling
* Meaningful JSON Error Responses

### Database

* PostgreSQL Integration
* Spring Data JPA
* Hibernate ORM

### DevOps

* Docker Support
* Docker Compose Configuration

---

## 🛠 Tech Stack

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT
* PostgreSQL
* Hibernate
* Maven
* Docker
* Docker Compose
* Lombok
* Postman

---

## 📁 Project Structure

```text
src
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── exception
├── repository
├── security
├── service
└── resources
```

---

## 🔐 Authentication

Authentication is implemented using **JSON Web Tokens (JWT)**.

After a successful login, the server returns a JWT token that must be included in the `Authorization` header for protected endpoints.

Example:

```http
Authorization: Bearer <jwt_token>
```

---

## 📌 Available APIs

| Method | Endpoint    | Description         |
| ------ | ----------- | ------------------- |
| POST   | `/register` | Register a new user |
| POST   | `/login`    | Authenticate user   |

> Additional APIs will be added as the project evolves.

---

## 🐳 Running with Docker

Build and start the application:

```bash
docker compose up --build
```

Stop the containers:

```bash
docker compose down
```

---

## ⚙️ Running Locally

Build the project:

```bash
mvn clean package
```

Run the application:

```bash
mvn spring-boot:run
```

---

## 📅 Roadmap

Upcoming features include:

* User Profile Management
* Code Submission Module
* Code Review Module
* Comments & Feedback
* Role-Based Authorization
* Pagination & Filtering
* Swagger / OpenAPI Documentation
* Unit & Integration Testing
* CI/CD Pipeline
* Cloud Deployment

---

## 📖 Learning Goals

This project focuses on learning and applying backend development concepts including:

* REST API Development
* Authentication & Authorization
* Database Design
* Spring Security
* Exception Handling
* Validation
* Docker
* Clean Architecture
* Secure Coding Practices

---

## 👨‍💻 Author

**Pranay**

Backend Developer | Java | Spring Boot | PostgreSQL

---

## 📄 License

This project is developed for learning, portfolio, and educational purposes.
