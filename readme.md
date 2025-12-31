# ☕ CoffeeShop POS Backend

Backend API untuk aplikasi **Coffee Shop POS (Point of Sale)** yang mendukung **multi-role (Admin, Merchant, Customer)** dengan **JWT Authentication**, dibangun menggunakan **Spring Boot** dan **PostgreSQL (Docker)**.

Project ini dikembangkan sebagai **portfolio backend engineer**, dengan fokus pada:
- Clean architecture
- Security (JWT & Spring Security)
- RESTful API design
- Scalable role-based access control

---

## 🚀 Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Spring Data JPA**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Maven**

---

## ✨ Main Features

### 🔐 Authentication & Security
- User registration (Sign Up)
- User login
- JWT-based authentication
- Password hashing using BCrypt
- Stateless authentication (no session)

### 👥 Role Management (In Progress)
- ADMIN
- MERCHANT
- CUSTOMER
- One user can have multiple roles
- Role-based authorization

### 🏪 Coffee Shop & Product (Planned)
- Merchant registration
- Coffee shop management
- Product & category CRUD
- Multi-merchant ready

---

## 🔑 Authentication Flow (JWT)

1. User login with email & password
2. Spring Security authenticates credentials
3. JWT token is generated if authentication succeeds
4. Client sends token in request header:
