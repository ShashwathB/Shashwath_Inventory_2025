# E-Commerce Inventory Management System

## 📌 Project Objective

To develop a production-ready, optimized  e-commerce inventory management system , capable of handling stock supply, reservations, cancellations, availability checks, and concurrency for e-commerce platforms.

---

## ✅ Functional Scope

### Inventory Management Module (Mandatory):
- Create supply for an item.
- Reserve item quantity based on customer demand.
- Cancel reservations.
- Query current item availability.
- Handle concurrency for simultaneous reservations on the same item.

---

## 🛠️ Technology Stack

| Component       | Technology                         |
|----------------|------------------------------------|
| Backend         | Java, Spring Boot, Spring MVC      |
| Persistence     | Spring Data JPA                    |
| Database        | H2 (In-memory)                     |
| Caching         | Redis                              |
| Build Tool      | Maven                              |
| API Testing     | Postman           |
| Version Control | Git & GitHub                       |

---

## 🧩 Project Structure

com.example.demo
├── controller
│   └── ItemController.java
├── dto
│   ├── CreateItemRequest.java
│   ├── ReserveRequest.java
│   └── SupplyRequest.java
├── entity
│   ├── Item.java
│   ├── Reservation.java
│   └── ReservationStatus.java
├── repo
│   ├── ItemRepository.java
│   └── ReservationRepository.java
├── service
│   ├── ItemService.java
│   └── ItemServiceImpl.java
└── DemoApplication.java
