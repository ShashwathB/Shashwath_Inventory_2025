package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateItemRequest;
import com.example.demo.dto.ReserveRequest;
import com.example.demo.dto.SupplyRequest;
import com.example.demo.entity.Item;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.ReservationStatus;
import com.example.demo.repo.ItemRepository;
import com.example.demo.repo.ReservationRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	public Item createItem(CreateItemRequest request) {
		Item item = new Item();
		item.setName(request.getName());
		item.setTotalQuantity(0);
		return itemRepository.save(item);
	}

	@Transactional
	@CacheEvict(value = "itemAvailability", key = "#itemId")
	public Item supplyItem(Long itemId, SupplyRequest request) {
		Item item = itemRepository.findByIdForUpdate(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found"));
		item.setTotalQuantity(item.getTotalQuantity() + request.getQuantity());
		return itemRepository.save(item);
	}

	@Transactional
	@CacheEvict(value = "itemAvailability", key = "#itemId")
	public Reservation reserveItem(Long itemId, ReserveRequest request) {
		Item item = itemRepository.findByIdForUpdate(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found"));
		int available = getAvailableQuantity(itemId);

		if (request.getQuantity() > available) {
			throw new IllegalArgumentException("Insufficient Quantity");
		}

		Reservation reservation = new Reservation();
		reservation.setItem(item);
		reservation.setReservedBy(request.getReservedBy());
		reservation.setReservedQuantity(request.getQuantity());
		reservation.setStatus(ReservationStatus.ACTIVE);

		return reservationRepository.save(reservation);
	}

	@Transactional
	@CacheEvict(value = "itemAvailability", key = "#reservation.getItem().getId()")
	public void cancelReservation(Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

		if (reservation.getStatus() == ReservationStatus.CANCELLED) {
			throw new IllegalStateException("Reservation is already cancelled");
		}

		reservation.setStatus(ReservationStatus.CANCELLED);
		reservationRepository.save(reservation);
	}

	@Cacheable(value = "itemAvailability", key = "#itemId")
	public int getAvailableQuantity(Long itemId) {
		Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found"));
		int totalQuantity = item.getTotalQuantity();

		List<Reservation> activeReservations = reservationRepository.findByItemAndStatus(item,
				ReservationStatus.ACTIVE);
		int reservedQuantity = activeReservations.stream().mapToInt(Reservation::getReservedQuantity).sum();

		return totalQuantity - reservedQuantity;
	}

}