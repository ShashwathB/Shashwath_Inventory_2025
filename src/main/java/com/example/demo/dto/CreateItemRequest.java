package com.example.demo.dto;

public class CreateItemRequest {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CreateItemRequest(String name) {
		super();
		this.name = name;
	}

}
