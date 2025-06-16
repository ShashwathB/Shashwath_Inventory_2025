package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "items")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(nullable = false)
	private int totalQuantity;

//	@Version
//	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

//	public Long getVersion() {
//		return version;
//	}
//
//	public void setVersion(Long version) {
//		this.version = version;
//	}
}
