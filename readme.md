# ☕ Coffeeshop Backend API

Backend REST API untuk aplikasi **Coffee Shop Platform** yang dibangun menggunakan **Spring Boot**, **JWT Authentication**, dan **Role-Based Access Control (RBAC)**.

Project ini dirancang sebagai **real-world backend system** dengan fokus pada:
- Clean architecture
- Business flow yang jelas
- Security best practice
- Code quality (SonarQube compliant)

Cocok digunakan sebagai **backend portfolio** untuk melamar posisi:
- Backend Engineer
- Java Developer
- Spring Boot Developer

---

## 🎯 Project Objective

Project ini bertujuan untuk menunjukkan kemampuan dalam:

- Mendesain **RESTful API** yang scalable
- Mengimplementasikan **JWT-based authentication**
- Menerapkan **Role & Permission management**
- Menangani **approval workflow (state machine)**
- Menulis kode yang **clean, maintainable, dan testable**
- Mengikuti **static code analysis (SonarQube rules)**

---

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Maven**
- **Docker & Docker Compose**
- **SonarQube**
- **Swagger / OpenAPI**

---

## 🧱 Architecture Overview

Project ini menggunakan **layered architecture**:


### Layer Responsibility
- **Controller**  
  Menangani HTTP request & response

- **Service**  
  Business logic, validation, dan workflow

- **Repository**  
  Database access (JPA)

- **DTO**  
  Contract request & response

- **Security**  
  Authentication & Authorization

---

## 🔐 Authentication & Security

### JWT Authentication
- Login menghasilkan **JWT token**
- Token dikirim melalui header:
- Stateless (tanpa session di server)

### Password Security
- Password disimpan menggunakan **BCrypt hashing**
- Tidak ada plaintext password di database

### Security Components
- `CustomUserDetails`
- `CustomUserDetailsService`
- `JwtAuthenticationFilter`

---

## 👥 User & Role Management

### User
- Login menggunakan **email**
- Satu user dapat memiliki **multiple roles**

### Roles
- `CUSTOMER`
- `MERCHANT`
- `ADMIN`

Relasi user–role menggunakan **Many-to-Many relationship**.

---

## 🏪 Merchant Registration Flow (Core Feature)

### 1️⃣ Customer Request Merchant
- User dengan role `CUSTOMER` dapat mengajukan request menjadi merchant
- Data disimpan ke tabel `merchant_requests`
- Status awal: `PENDING`

### 2️⃣ Business Rules
- User **tidak bisa request ulang** jika masih `PENDING`
- Jika sudah `APPROVED` atau `REJECTED`, request baru bisa diproses sesuai aturan

### 3️⃣ Admin Approval Flow
Admin dapat:
- **Approve** merchant
- **Reject** merchant (dengan alasan)

Jika di-approve:
- Status → `APPROVED`
- Role `MERCHANT` otomatis ditambahkan ke user

Jika di-reject:
- Status → `REJECTED`
- Alasan penolakan disimpan

### 4️⃣ Merchant Status Enum
- `PENDING`
- `APPROVED`
- `REJECTED`

---

## 🛠 Admin Features

- List merchant request (ALL / PENDING / APPROVED / REJECTED)
- Approve merchant request
- Reject merchant request dengan alasan
- Role assignment otomatis

Semua endpoint admin dilindungi dengan:
```java
@PreAuthorize("hasRole('ADMIN')")

📦 Standard API Response
{
  "status": "T",
  "message": "Operation success",
  "data": {}
}
