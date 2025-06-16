package com.example.demo.dto;

public class ReserveRequest {

	private int quantity;
	private String reservedBy;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getReservedBy() {
		return reservedBy;
	}

	public void setReservedBy(String reservedBy) {
		this.reservedBy = reservedBy;
	}

	public ReserveRequest(int quantity, String reservedBy) {
		super();
		this.quantity = quantity;
		this.reservedBy = reservedBy;
	}
}
