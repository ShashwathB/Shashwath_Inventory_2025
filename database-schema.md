# 📊 Database Schema - Inventory Management System

This document outlines the normalized relational database schema used in the **Inventory Management System**.

---

## 📌 Tables Overview

### 1. `item`

Stores inventory item information.

| Column Name     | Type      | Constraints                    |
|-----------------|-----------|--------------------------------|
| `id`            | BIGINT    | Primary Key, Auto-generated    |
| `name`          | VARCHAR   | Not Null, Unique               |
| `total_quantity`| INT       | Not Null                       |

---

### 2. `reservation`

Stores reservation details made against items.

| Column Name         | Type      | Constraints                          |
|---------------------|-----------|--------------------------------------|
| `id`                | BIGINT    | Primary Key, Auto-generated          |
| `item_id`           | BIGINT    | Foreign Key → `item(id)`             |
| `reserved_by`       | VARCHAR   | Not Null                             |
| `reserved_quantity` | INT       | Not Null                             |
| `status`            | VARCHAR   | Enum: 'ACTIVE', 'CANCELLED'          |

---

## 🔗 Relationships

- One `item` can have **many** `reservations`
- `reservation.item_id` is a foreign key referencing `item.id`

---

## 🛡️ Concurrency Handling
- **Pessimistic Locking**: Done using `findByIdForUpdate(itemId)` in transactional service methods

---

## 🗃️ Schema Features

- **Normalized structure** with separate entities for inventory and reservations
- Integrated with **Redis caching** for available quantity lookups
- **H2 in-memory DB** for fast testing and development

---

## 📂 Database Setup

- Spring Boot auto-generates schema via JPA
- `application.properties` must include:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
