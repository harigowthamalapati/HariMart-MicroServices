package com.harimart.inventoryservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.harimart.inventoryservice.dto.InventoryRequest;
import com.harimart.inventoryservice.dto.InventoryResponse;
import com.harimart.inventoryservice.dto.StockRequest;
import com.harimart.inventoryservice.service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/HMart/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse>
    createInventory(
            @Valid @RequestBody
            InventoryRequest request) {

        return ResponseEntity.ok(
                inventoryService
                        .createInventory(request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse>
    getInventory(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                inventoryService
                        .getInventoryByProductId(productId));
    }

    @PutMapping("/{productId}/add-stock")
    public ResponseEntity<InventoryResponse>
    addStock(
            @PathVariable Long productId,
            @Valid @RequestBody
            StockRequest request) {

        return ResponseEntity.ok(
                inventoryService
                        .addStock(productId, request));
    }

    @PutMapping("/{productId}/reduce-stock")
    public ResponseEntity<InventoryResponse>
    reduceStock(
            @PathVariable Long productId,
            @Valid @RequestBody
            StockRequest request) {

        return ResponseEntity.ok(
                inventoryService
                        .reduceStock(productId, request));
    }
}