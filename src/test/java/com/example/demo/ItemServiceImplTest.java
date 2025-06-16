package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.ReserveRequest;
import com.example.demo.dto.SupplyRequest;
import com.example.demo.entity.*;
import com.example.demo.repo.ItemRepository;
import com.example.demo.repo.ReservationRepository;
import com.example.demo.service.ItemServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ReservationRepository reservationRepository;

    private Item sampleItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleItem = new Item();
        sampleItem.setId(1L);
        sampleItem.setName("ItemA");
        sampleItem.setTotalQuantity(100);
    }

    @Test
    void testSupplyItem() {
        SupplyRequest request = new SupplyRequest(10);

        when(itemRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(sampleItem));
        when(itemRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Item updatedItem = itemService.supplyItem(1L, request);

        assertEquals(110, updatedItem.getTotalQuantity());
    }

    @Test
    void testReserveItem_Success() {
        ReserveRequest request = new ReserveRequest(10,"User1");

        when(itemRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(sampleItem));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(sampleItem));
        when(reservationRepository.findByItemAndStatus(sampleItem, ReservationStatus.ACTIVE)).thenReturn(List.of());
        when(reservationRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Reservation reservation = itemService.reserveItem(1L, request);

        assertEquals("User1", reservation.getReservedBy());
        assertEquals(10, reservation.getReservedQuantity());
        assertEquals(ReservationStatus.ACTIVE, reservation.getStatus());
    }

    @Test
    void testReserveItem_InsufficientQuantity() {
        ReserveRequest request = new ReserveRequest(200,"User1");

        when(itemRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(sampleItem));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(sampleItem));
        when(reservationRepository.findByItemAndStatus(sampleItem, ReservationStatus.ACTIVE)).thenReturn(List.of());

        assertThrows(IllegalArgumentException.class, () -> itemService.reserveItem(1L, request));
    }

    @Test
    void testCancelReservation_Success() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setItem(sampleItem);
        reservation.setStatus(ReservationStatus.ACTIVE);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertDoesNotThrow(() -> itemService.cancelReservation(1L));
        verify(reservationRepository).save(reservation);
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    }

    @Test
    void testCancelReservation_AlreadyCancelled() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setItem(sampleItem);
        reservation.setStatus(ReservationStatus.CANCELLED);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertThrows(IllegalStateException.class, () -> itemService.cancelReservation(1L));
    }

    @Test
    void testGetAvailableQuantity() {
        Reservation r1 = new Reservation();
        r1.setReservedQuantity(20);
        Reservation r2 = new Reservation();
        r2.setReservedQuantity(10);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(sampleItem));
        when(reservationRepository.findByItemAndStatus(sampleItem, ReservationStatus.ACTIVE)).thenReturn(List.of(r1, r2));

        int available = itemService.getAvailableQuantity(1L);

        assertEquals(70, available); // 100 - (20 + 10)
    }

    @Test
    void testGetAvailableQuantity_ItemNotFound() {
        when(itemRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> itemService.getAvailableQuantity(2L));
    }
}
