# 🎓 University Management System (UMS)

A **full‑stack University Management System** built using **React** for the frontend and **Spring Boot** for the backend. This project is designed to handle **complex university operations**, including **multi‑college management, dynamic roles & permissions (RBAC), students, staff, programs, and academic workflows**.

> 🚀 This project is intended to demonstrate **real‑world system design**, **scalable backend architecture**, and **modern frontend routing**.

---

## 🧩 System Overview

The University Management System supports **multiple universities**, each containing **multiple colleges**, with **shared and college‑specific staff**, **programs**, and **students**.

### 👥 User Types

- **Staff (University & College level)**

  - Professors
  - Assistant Professors
  - Intern Professors
  - HODs
  - Principals
  - Directors
  - Office Staff
  - Mentors
  - Non‑academic staff (paper management, maintenance, etc.)

- **Students**

  - Registered under a specific **Program**
  - Assigned a **unique university enrollment ID**

---

## 🔐 Roles & Permissions (RBAC)

The system uses a **dynamic Role‑Based Access Control (RBAC)** model.

### Features:

- Roles are **not hard‑coded**
- New roles can be created at runtime
- Permissions decide **which operations a user can perform**

Example:

```text
Role: HOD
Permissions:
- CREATE_SUBJECT
- ASSIGN_PROFESSOR
- VIEW_STUDENT_REPORTS
```

This design allows:

- Fine‑grained access control
- Enterprise‑level authorization
- Easy future extension

---

## 🏗️ Tech Stack

### Frontend (Client)

- **React**
- **React Router v7 (SPA routing)**
- **Context API**
- **Axios**
- **JWT‑based Authentication**

### Backend (Server)

- **Spring Boot**
- **Spring Security**
- **Spring Data JPA (Hibernate)**
- **RESTful APIs**
- **Role & Permission based Authorization**

### Database

- **MySQL**

### Build & DevOps

- Docker
- Jenkins (CI/CD)

<!-- ---

## 📁 Project Structure

### Backend (Spring Boot)

```
backend/
 ├── controller/
 ├── service/
 ├── repository/
 ├── entity/
 ├── security/
 ├── dto/
 └── config/
```

### Frontend (React)

```
frontend/
 ├── src/
 │   ├── pages/
 │   ├── components/
 │   ├── routes/
 │   ├── context/
 │   └── services/
 └── public/
``` -->

---

## 🌐 Routing Strategy (SPA)

- React handles **all UI routes** using React Router
- Spring Boot forwards frontend routes to `index.html`

```java
@GetMapping(value = "/ums/**")
public String reactEndpoint() {
    return "forward:/index.html";
}

// And there are other page handlers home, about, achivements, alumni page etc. this pages are server generated using thymeleaf templates so they are SEO friendly.
```

---

## 🔄 API Design Philosophy

- REST‑based architecture
- Clear separation of concerns
- HTTP status codes for error handling

Examples:

| Status Code | Meaning                   |
| ----------- | ------------------------- |
| 401         | Unauthorized              |
| 403         | Forbidden (No Permission) |
| 404         | Resource Not Found        |
| 409         | Conflict                  |

---

## 🔑 Authentication Flow

1. User logs in
2. Backend validates credentials
3. JWT token is issued
4. Frontend stores token securely
5. Permissions are validated on each request

---

## 🧠 Key Design Highlights

✔ Multi‑University support
✔ Multi‑College hierarchy
✔ Dynamic Role & Permission model
✔ College‑level & University‑level staff
✔ Scalable database schema
✔ SPA + API clean separation

---

## 🚀 Getting Started

> To run this application is very easy, just need to install docker on your local system and clone the repository and open the terminal in root of the repository

```sh
 docker compose up -d
```

###

<!--
### Backend Setup

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend Setup

```bash
cd frontend
npm install
npm run dev
``` -->

---

## 📌 Future Enhancements

- Fee & Payment System
- Timetable Generator
- Microservices Migration

---

## 🤝 Contribution

Contributions are welcome! Feel free to fork the repository and submit pull requests.

---

## 📜 License

This project is licensed under the MIT License.

---

⭐ If you like this project, consider giving it a star!
