# 📘 API Documentation - E-commerce Inventory Management System

This document provides full API reference for the production-ready Inventory Management System. It covers request/response structures, concurrency handling, caching, and error responses.

---

## ✅ Base URL

http://localhost:8080/inventory

---

## 🛒 1. Create a New Item

- **Method**: `POST`
- **Endpoint**: `/items`
- **Description**: Creates a new inventory item.
- **Request Body**:

{
  "name": "Laptop"
}
Response: 200 OK

{
  "id": 1,
  "name": "Laptop",
  "totalQuantity": 0
}
📦 2. Supply Quantity to an Item
Method: POST

Endpoint: /items/{itemId}/supply

Path Variable: itemId — ID of the item

Request Body:

{
  "quantity": 50
}
Response: 200 OK

{
  "id": 1,
  "name": "Laptop",
  "totalQuantity": 50
}
📥 3. Reserve Item Quantity
Method: POST

Endpoint: /items/{itemId}/reserve

Path Variable: itemId — ID of the item to reserve

Request Body:


{
  "reservedBy": "John",
  "quantity": 10
}
Response: 200 OK

Reservation successful
❌ 4. Cancel a Reservation
Method: POST

Endpoint: /items/reservations/{reservationId}/cancel

Path Variable: reservationId — ID of the reservation

Response: 200 OK

Reservation cancelled
📊 5. Get Available Quantity
Method: GET

Endpoint: /items/{itemId}/availability

Path Variable: itemId — ID of the item

Response: 200 OK
