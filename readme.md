# ☕ Coffeeshop Backend API

Backend REST API untuk aplikasi **Coffee Shop** menggunakan **Spring Boot**, **JWT Authentication**, dan **Role-Based Access Control (RBAC)**.

Project ini mendukung:
- User authentication & authorization
- Merchant registration flow (approval system)
- Admin management
- Standardized API response
- PostgreSQL + Docker

---

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot 4**
- **Spring Security (JWT)**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Maven**
- **Swagger / OpenAPI**

---

## 📦 Features

### 🔐 Authentication & Security
- JWT-based authentication
- Stateless session
- Password encryption (BCrypt)
- Custom `UserDetailsService`
- Role-based endpoint protection

### 👥 User & Role
- User registration & login
- Multiple roles per user
- Roles:
  - `CUSTOMER`
  - `MERCHANT`
  - `ADMIN`

### 🏪 Merchant Flow
- Customer dapat mengajukan request menjadi merchant
- Status merchant:
  - `PENDING`
  - `APPROVED`
  - `REJECTED`
- User **tidak bisa request ulang** jika masih `PENDING`
- Admin dapat approve / reject merchant
- Role otomatis bertambah saat merchant di-approve

### 📄 Standard API Response
Semua endpoint menggunakan format response yang konsisten:

```json
{
  "status": "T | F",
  "message": "string",
  "data": {}
}
