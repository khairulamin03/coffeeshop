# ☕ Coffeeshop Backend API

Backend REST API untuk aplikasi **Coffee Shop** menggunakan **Spring Boot**, **JWT Authentication**, dan **Role-Based Access Control (RBAC)**.

Project ini mendukung:
- User authentication & authorization
- Merchant registration flow (approval & rejection system)
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
- Role-based endpoint protection (`@PreAuthorize`)

---

### 👥 User & Role
- User registration & login
- Multiple roles per user (Many-to-Many)
- Default role saat signup: `CUSTOMER`
- Roles:
  - `CUSTOMER`
  - `MERCHANT`
  - `ADMIN`

---

## 🏪 Merchant Flow (Customer → Admin Approval)

### Status Merchant
- `PENDING`
- `APPROVED`
- `REJECTED`

---

### 1️⃣ Customer Request Merchant
- Endpoint hanya dapat diakses oleh role `CUSTOMER`
- Sistem akan mengecek:
  - Apakah user memiliki merchant request `PENDING`
- Jika masih `PENDING` → request **ditolak tanpa exception**
- Jika tidak ada → request baru dibuat dengan status `PENDING`

---

### 2️⃣ Admin Approve / Reject Merchant
- Endpoint hanya dapat diakses oleh role `ADMIN`
- Admin dapat:
  - **Approve merchant**
  - **Reject merchant dengan alasan**

---

### 3️⃣ Role Update Logic
- Saat merchant di-approve:
  - Role `MERCHANT` **ditambahkan**, bukan menggantikan
- Contoh:
