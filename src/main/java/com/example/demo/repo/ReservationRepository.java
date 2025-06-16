package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Item;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByItemAndStatus(Item item, ReservationStatus status);
}
