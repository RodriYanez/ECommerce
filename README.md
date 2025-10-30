# 🛍️ E-Commerce Prices API

This project implements a **RESTful service** for an e-commerce platform.  
It allows retrieving the **applicable price for a product** at a specific date and time, based on **brand**, **product**, and **pricing priority**.

---

## 📦 Technologies Used

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **MapStruct**
- **H2 Database (in-memory)**
- **OpenAPI / Swagger UI**
- **JUnit 5 + Mockito**
- **Lombok**

---

## ⚙️ Prerequisites

Before running the application, make sure you have:

- [Java 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- (Optional) [Postman](https://www.postman.com/) for testing

---

## 🚀 Running the Application

1. **Clone the repository**
   ```bash
   git clone https://github.com/RodriYanez/ECommerce.git
   
2. **Build And Run**
    ```bash
   mvn clean spring-boot:run

3. **Initial data**

The project automatically loads example data into the **in-memory H2 database** through **[data.sql](./src/main/resources/data.sql)** file located in src/main/resources

## 📖 Swagger Documentation

Once running, you can access the interactive API documentation at: http://localhost:8080/swagger-ui/index.html

## H2 Database Access

To inspect the in-memory database, open: http://localhost:8080/h2-console

### ⚙️ Connection settings:

**JDBC URL**: jdbc:h2:mem:ecommerce_db

**User**: admin

**Password**: admin

## Postman collection tests

You can import the postman collection ([collection](./src/main/resources/postman/ECommerce_tests.postman_collection.json)) to run the E2E tests.

### Tests in the postman collection

1. Request made at 2020-06-14 10:00 for the product 35455 and brand 1.
2. Request made at 2020-06-14 16:00 for the product 35455 and brand 1.
3. Request made at 2020-06-14 21:00 for the product 35455 and brand 1.
4. Request made at 2020-06-15 10:00 for the product 35455 and brand 1.
5. Request made at 2020-06-16 21:00 for the product 35455 and brand 1.
6. Request where not price found.
7. Request with invalid arguments.

## Project structure

```bash
src/
├── main/
│   ├── java/com/kairos/ecommerce/
│   │   ├── application/usecases/      → Business logic (use cases)
│   │   ├── domain/models/             → Domain entities
│   │   ├── domain/port/               → Hexagonal architecture ports
│   │   ├── infrastructure/
│   │   │   ├── persistence/           → JPA adapters and mappers
│   │   │   ├── rest/                  → REST controllers and mappers
│   │   │   └── exceptions/            → Custom exceptions
│   └── resources/
│       ├── postman                    → Postman collection with tests.
│       ├── openapi                    → OpenAPI definition
│       ├── application.yml            → Spring Boot configuration
│       ├── data.sql                   → H2 initialization data
├── test/                              → Unit and integration tests
