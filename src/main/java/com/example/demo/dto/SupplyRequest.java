package com.example.demo.dto;

public class SupplyRequest {
	
	private int quantity;

	public SupplyRequest(int quantity) {
		super();
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}