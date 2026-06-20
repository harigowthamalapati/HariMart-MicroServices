package com.harimart.inventoryservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harimart.inventoryservice.entity.Inventory;

public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);
    
    List<Inventory> findByAvailableQuantityLessThan(
            Integer threshold);
}