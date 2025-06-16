package com.example.demo;

import com.example.demo.controller.ItemController;
import com.example.demo.dto.CreateItemRequest;
import com.example.demo.dto.SupplyRequest;
import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ItemService itemService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreateItem() throws Exception {
		CreateItemRequest request = new CreateItemRequest("Laptop");
		Item created = new Item();
		created.setId(1L);
		created.setName("Laptop");

		Mockito.when(itemService.createItem(any(CreateItemRequest.class))).thenReturn(created);

		mockMvc.perform(post("/inventory/items").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Laptop")));
	}

	@Test
	void testSupplyItem() throws Exception {
		SupplyRequest request = new SupplyRequest(50);
		Item updated = new Item();
		updated.setId(1L);
		updated.setName("Laptop");

		Mockito.when(itemService.supplyItem(eq(1L), any(SupplyRequest.class))).thenReturn(updated);

		mockMvc.perform(post("/inventory/items/1/supply").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)));
	}

	@Test
	void testCancelReservation() throws Exception {
		Mockito.doNothing().when(itemService).cancelReservation(10L);

		mockMvc.perform(post("/inventory/items/reservations/10/cancel")).andExpect(status().isOk())
				.andExpect(content().string("Reservation cancelled"));
	}

	@Test
	void testGetAvailableQuantity() throws Exception {
		Mockito.when(itemService.getAvailableQuantity(1L)).thenReturn(25);

		mockMvc.perform(get("/inventory/items/1/availability")).andExpect(status().isOk())
				.andExpect(content().string("25"));
	}
}
