package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Item;

import jakarta.persistence.LockModeType;

public interface ItemRepository extends JpaRepository<Item, Long> {
    
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Item i WHERE i.id = :itemId")
    Optional<Item> findByIdForUpdate(@Param("itemId") Long itemId);
}