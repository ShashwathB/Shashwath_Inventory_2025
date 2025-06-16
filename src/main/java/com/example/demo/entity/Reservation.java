package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false)
	private Item item;

	private int reservedQuantity;

	private String reservedBy;

	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getReservedQuantity() {
		return reservedQuantity;
	}

	public void setReservedQuantity(int reservedQuantity) {
		this.reservedQuantity = reservedQuantity;
	}

	public String getReservedBy() {
		return reservedBy;
	}

	public void setReservedBy(String reservedBy) {
		this.reservedBy = reservedBy;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
}
