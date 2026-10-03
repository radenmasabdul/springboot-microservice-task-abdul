# 📚 Book Management — Book Management API

<p align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="50" alt="Java" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="50" alt="Spring Boot" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/maven/maven-original.svg" width="50" alt="Maven" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" width="50" alt="PostgreSQL" />
  <img src="https://cdn.simpleicons.org/jsonwebtokens" width="50" alt="JWT" />
</p>

<p>
  Book Management is a RESTful API built with Spring Boot for managing books and users.
  The application provides secure authentication and role-based authorization, allowing administrators to manage book and user data while regular users have read-only access.
  It also includes ISBN validation, search, pagination, and centralized error handling for a reliable and maintainable backend service.
</p>

## 🚀 Key Features

* 🔐 JWT authentication using HttpOnly cookies
* 👤 User management with CRUD operations
* 📚 Book management with CRUD operations
* 🛡️ Role-based authorization with `ADMINISTRATOR` and `USER` roles
* 🔍 Search books and users by relevant fields
* 📄 Pagination for book and user listing
* 🏷️ ISBN validation and automatic ISBN-13 check digit generation
* ✏️ Full and partial book updates using `PUT` and `PATCH`
* ⚠️ Centralized exception handling with consistent API error responses
* 🔒 Password hashing using BCrypt
* 🌐 Global CORS configuration for frontend integration
* 🗄️ PostgreSQL database integration using Spring Data JPA
* ⚙️ Environment-based configuration for sensitive application settings
* 🧪 API testing using Postman

## 🛠️ Tech Stack

* **Language**: Java 21
* **Framework**: Spring Boot 4.1.1
* **Build Tool**: Maven
* **Database**: PostgreSQL
* **ORM**: Spring Data JPA / Hibernate
* **Security**: Spring Security
* **Authentication**: JWT
* **Password Hashing**: BCrypt
* **Validation**: Jakarta Bean Validation
* **Code Generation**: Lombok
* **API**: RESTful API
* **API Testing**: Postman
* **Database Migration**: None
* **Code Quality**: Maven Compiler / Spring Boot validation

## 📋 Prerequisites

Before running Book Management locally, make sure you have installed:

* **Java** 21 or higher
* **Maven** 3.9 or higher
* **PostgreSQL** 18 or compatible version
* **Git**
* **Postman** (recommended for API testing)

## ⚡ Quick Start

### 1. Clone Repository

```bash
git clone https://github.com/radenmasabdul/springboot-microservice-task-abdul.git
cd book-management
```

### 2. Setup PostgreSQL

Create a PostgreSQL database for the application.

```sql
CREATE DATABASE book_management;
```

Make sure PostgreSQL is running before starting the application.

### 3. Setup Environment

Create a `.env` file or configure the required environment variables according to your local environment.

```bash
DB_URL=jdbc:postgresql://localhost:5432/book_management
DB_USERNAME=your-db-username
DB_PASSWORD=your-db-password
JWT_SECRET=your-jwt-secret
JWT_EXPIRATION=28800000
```

These variables are used for database connectivity and JWT authentication.

> ⚠️ Do not commit real credentials or secrets to the repository. Use your own local environment configuration.

### 4. Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

### 5. Build the Application

To build the project:

```bash
mvn clean install
```

## 🧪 API Testing

The API has been tested using Postman to verify authentication, authorization, CRUD operations, validation, pagination, search, and error handling.

### Postman Collection

The Postman collection is included in the repository:

```text
postman/
└── Book Management.postman_collection.json
```

The collection includes requests for:

* 🔐 Authentication and logout
* 👤 User Management
* 📚 Book Management
* 🔍 Search and pagination
* 🏷️ ISBN validation
* 🛡️ Role-based authorization
* ⚠️ Validation and error handling
* 🔄 PUT and PATCH operations

Import the collection into Postman and configure the required environment variables before running the requests.

### Sample Requests

**Base URL**

```text
http://localhost:8080
```

**Login**

```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json
```

```json
{
  "email": "admin@example.com",
  "password": "password123"
}
```

A successful login sets the JWT as an HttpOnly cookie.

**Get Books**

```http
GET http://localhost:8080/api/books?page=0&size=10
```

**Search Books**

```http
GET http://localhost:8080/api/books?search=clean&page=0&size=10
```

**Create Book**

```http
POST http://localhost:8080/api/books
Content-Type: application/json
```

```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978602123456",
  "publishedDate": "2008-08-01"
}
```

**Update Book**

```http
PUT http://localhost:8080/api/books/{id}
Content-Type: application/json
```

```json
{
  "title": "Clean Code Updated",
  "author": "Robert C. Martin",
  "isbn": "978602123456",
  "publishedDate": "2008-08-01"
}
```

**Partial Update Book**

```http
PATCH http://localhost:8080/api/books/{id}
Content-Type: application/json
```

```json
{
  "title": "Clean Code - Second Edition"
}
```

> The create and update examples use a 12-digit ISBN input that is validated and converted into a valid ISBN-13 by the API.

## 📁 Project Structure

```text
book-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/bookmanagement/
│   │   │       ├── user/                         # User management module
│   │   │       │   ├── controller/               # User REST controllers
│   │   │       │   ├── service/                  # User business logic
│   │   │       │   ├── repository/               # User data access
│   │   │       │   ├── entity/                   # User entities and enums
│   │   │       │   ├── dto/                      # User request/response DTOs
│   │   │       │   │   ├── request/
│   │   │       │   │   └── response/
│   │   │       │   └── auth/                     # Authentication-related components
│   │   │       │
│   │   │       ├── book/                         # Book management module
│   │   │       │   ├── controller/               # Book REST controllers
│   │   │       │   ├── service/                  # Book business logic
│   │   │       │   ├── repository/               # Book data access
│   │   │       │   ├── entity/                   # Book entities
│   │   │       │   └── dto/                      # Book request/response DTOs
│   │   │       │
│   │   │       ├── common/                       # Shared application components
│   │   │       │   ├── dto/                      # Common API response DTOs
│   │   │       │   ├── exception/                # Global exception handling
│   │   │       │   ├── mapper/                   # Entity/DTO mapping
│   │   │       │   ├── specification/            # Dynamic search specifications
│   │   │       │   └── util/                     # Shared utility classes
│   │   │       │
│   │   │       └── security/                     # Security configuration
│   │   │
│   │   └── resources/
│   │       ├── application.properties            # Application configuration
│   │       └── ...
│   │
│   └── test/
│       └── java/                                  # Application tests
│
├── postman/
│   └── Book Management.postman_collection.json   # Postman collection
│
├── .env                                           # Environment variables
├── .gitignore                                     # Git ignored files
├── pom.xml                                        # Maven dependencies and configuration
├── mvnw                                           # Maven wrapper
├── mvnw.cmd                                       # Maven wrapper for Windows
├── README.md                                      # Project documentation
└── ...
```

### 📂 Main Modules

* **`user/`** — Handles user management, authentication, and user-related operations.
* **`book/`** — Handles book management, CRUD operations, search, pagination, and ISBN processing.
* **`common/`** — Contains reusable components shared across application modules, including exception handling, API DTOs, specifications, and utilities.
* **`security/`** — Contains Spring Security and JWT-related configuration.
* **`resources/`** — Contains application configuration and other runtime resources.
* **`postman/`** — Contains the Postman collection used for API testing.

## 👨‍💻 Author

**radenmasabdul**
- GitHub: [@radenmasabdul](https://github.com/radenmasabdul)
