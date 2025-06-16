package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateItemRequest;
import com.example.demo.dto.ReserveRequest;
import com.example.demo.dto.SupplyRequest;
import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;

@RestController
@RequestMapping("/inventory")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Create a new item
    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@RequestBody CreateItemRequest request) {
        Item created = itemService.createItem(request);
        return ResponseEntity.ok(created);
    }

    // Supply quantity to an existing item
    @PostMapping("/items/{itemId}/supply")
    public ResponseEntity<Item> supplyItem(@PathVariable Long itemId, @RequestBody SupplyRequest request) {
        Item updated = itemService.supplyItem(itemId, request);
        return ResponseEntity.ok(updated);
    }

    // Reserve item quantity
    @PostMapping("/items/{itemId}/reserve")
    public ResponseEntity<String> reserveItem(@PathVariable Long itemId, @RequestBody ReserveRequest request) {
        itemService.reserveItem(itemId, request);
        return ResponseEntity.ok("Reservation successful");
    }

    // Cancel reservation
    @PostMapping("/items/reservations/{reservationId}/cancel")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        itemService.cancelReservation(reservationId);
        return ResponseEntity.ok("Reservation cancelled");
    }

    // Check item availability
    @GetMapping("/items/{itemId}/availability")
    public ResponseEntity<Integer> getAvailableQuantity(@PathVariable Long itemId) {
        int available = itemService.getAvailableQuantity(itemId);
        return ResponseEntity.ok(available);
    }
}
