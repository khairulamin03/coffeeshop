# ☕ Coffeeshop Backend API

Backend REST API untuk aplikasi **Coffee Shop Platform** yang dibangun menggunakan **Spring Boot**, **JWT Authentication**, dan **Role-Based Access Control (RBAC)**.

Project ini dirancang sebagai **real-world backend system**, mencakup:
- Authentication & Authorization
- Multi-role user system
- Merchant registration & approval flow
- Admin management
- Clean architecture & standardized API response

---

## 📌 Project Purpose

Project ini dibuat untuk menunjukkan kemampuan dalam:
- Merancang **secure backend system**
- Mengimplementasikan **JWT-based authentication**
- Mengelola **role & permission**
- Menangani **business rules & edge cases**
- Menulis **clean, maintainable Spring Boot code**

Cocok sebagai **backend portfolio** untuk posisi:
- Backend Engineer
- Java Developer
- Spring Boot Developer

---

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot 4**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Maven**
- **Docker & Docker Compose**
- **Swagger / OpenAPI**

---

## 🧱 Architecture Overview

Project menggunakan pendekatan **layered architecture**:


Dengan pemisahan yang jelas antara:
- **Controller** → HTTP handling
- **Service** → Business logic
- **Repository** → Data access
- **Security** → Authentication & Authorization
- **DTO** → Request / Response contract

---

## 🔐 Authentication & Security

### JWT Authentication
- Login menghasilkan **JWT token**
- Token dikirim via `Authorization: Bearer <token>`
- Stateless (tanpa session server-side)

### Password Security
- Password disimpan menggunakan **BCrypt hashing**
- Tidak ada plaintext password di database

### Custom Security
- `CustomUserDetails`
- `CustomUserDetailsService`
- `JwtAuthenticationFilter`

---

## 👥 User & Role System

### User
- User login menggunakan **email**
- Satu user dapat memiliki **lebih dari satu role**

### Roles
- `CUSTOMER`
- `MERCHANT`
- `ADMIN`

Role disimpan menggunakan **Many-to-Many relationship**.

---

## 🏪 Merchant Flow (Core Feature)

### 1️⃣ Customer Request Merchant
- User dengan role `CUSTOMER` dapat mengajukan request menjadi merchant
- Data disimpan sebagai `MerchantRequest`
- Status awal: `PENDING`

### 2️⃣ Business Rules
- User **tidak bisa request ulang** jika status masih `PENDING`
- Sistem akan mengembalikan response:
  - Status request
  - Waktu pengajuan

### 3️⃣ Admin Approval
- Admin dapat:
  - **Approve** merchant
  - **Reject** merchant (dengan alasan)
- Jika di-approve:
  - Status → `APPROVED`
  - Role `MERCHANT` otomatis ditambahkan ke user

### 4️⃣ Merchant Status
- `PENDING`
- `APPROVED`
- `REJECTED`

---

## 🛠 Admin Features

- List merchant request berdasarkan status
- Approve merchant
- Reject merchant dengan alasan
- Role assignment otomatis

Semua endpoint admin dilindungi dengan:
```java
@PreAuthorize("hasRole('ADMIN')")
