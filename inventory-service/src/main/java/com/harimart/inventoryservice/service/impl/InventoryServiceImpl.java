package com.harimart.inventoryservice.service.impl;

import org.springframework.stereotype.Service;

import com.harimart.inventoryservice.dto.InventoryRequest;
import com.harimart.inventoryservice.dto.InventoryResponse;
import com.harimart.inventoryservice.dto.StockRequest;
import com.harimart.inventoryservice.entity.Inventory;
import com.harimart.inventoryservice.exception.ResourceNotFoundException;
import com.harimart.inventoryservice.repository.InventoryRepository;
import com.harimart.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryResponse createInventory(
            InventoryRequest request) {

        Inventory inventory = Inventory.builder()
                .productId(request.productId())
                .availableQuantity(request.availableQuantity())
                .reservedQuantity(0)
                .warehouseLocation(request.warehouseLocation())
                .build();

        Inventory savedInventory =
                inventoryRepository.save(inventory);

        return mapToResponse(savedInventory);
    }

    @Override
    public InventoryResponse getInventoryByProductId(
            Long productId) {

        Inventory inventory =
                inventoryRepository.findByProductId(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found for productId: "
                                                + productId));

        return mapToResponse(inventory);
    }

    @Override
    public InventoryResponse addStock(
            Long productId,
            StockRequest request) {

        Inventory inventory =
                inventoryRepository.findByProductId(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found"));

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity()
                        + request.quantity());

        Inventory updatedInventory =
                inventoryRepository.save(inventory);

        return mapToResponse(updatedInventory);
    }

    @Override
    public InventoryResponse reduceStock(
            Long productId,
            StockRequest request) {

        Inventory inventory =
                inventoryRepository.findByProductId(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found"));

        if (inventory.getAvailableQuantity()
                < request.quantity()) {

            throw new IllegalArgumentException(
                    "Insufficient stock available");
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity()
                        - request.quantity());

        Inventory updatedInventory =
                inventoryRepository.save(inventory);

        return mapToResponse(updatedInventory);
    }

    private InventoryResponse mapToResponse(
            Inventory inventory) {

        return new InventoryResponse(
                inventory.getId(),
                inventory.getProductId(),
                inventory.getAvailableQuantity(),
                inventory.getReservedQuantity(),
                inventory.getWarehouseLocation()
        );
    }
}