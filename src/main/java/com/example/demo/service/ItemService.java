package com.example.demo.service;

import com.example.demo.dto.CreateItemRequest;
import com.example.demo.dto.ReserveRequest;
import com.example.demo.dto.SupplyRequest;
import com.example.demo.entity.Item;
import com.example.demo.entity.Reservation;

public interface ItemService {
	
	Item createItem(CreateItemRequest request);

	Item supplyItem(Long itemId, SupplyRequest request);

	Reservation reserveItem(Long itemId, ReserveRequest request);

	void cancelReservation(Long reservationId);

	int getAvailableQuantity(Long itemId);
}