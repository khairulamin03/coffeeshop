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
- Multiple roles per user (Many-to-Many)
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
- User **tidak bisa request ulang** jika status masih `PENDING`
- Admin dapat approve / reject merchant
- Role `MERCHANT` otomatis ditambahkan saat request di-approve
- Sistem aman dari double request

---

## 🔄 Merchant Business Flow

### 1️⃣ Customer Request Merchant
- Endpoint hanya bisa diakses oleh role `CUSTOMER`
- Sistem akan cek:
  - Apakah user sudah memiliki request `PENDING`
- Jika ada → response gagal (tanpa exception)
- Jika tidak ada → request dibuat dengan status `PENDING`

### 2️⃣ Admin Review Merchant
- Admin melihat daftar merchant request
- Admin dapat:
  - Approve → status jadi `APPROVED`, role `MERCHANT` ditambahkan
  - Reject → status jadi `REJECTED`

### 3️⃣ Role Update
- User **tidak kehilangan role lama**
- Contoh:
