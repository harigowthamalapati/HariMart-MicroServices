package com.harimart.inventoryservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.harimart.inventoryservice.dto.InventoryRequest;
import com.harimart.inventoryservice.dto.InventoryResponse;
import com.harimart.inventoryservice.dto.ReleaseStockRequest;
import com.harimart.inventoryservice.dto.ReserveStockRequest;
import com.harimart.inventoryservice.dto.StockAvailabilityResponse;
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
    
    @GetMapping("/availability/{productId}")
    public ResponseEntity<StockAvailabilityResponse>
    checkAvailability(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                inventoryService
                        .checkStockAvailability(
                                productId));
    }
    
    @PostMapping("/reserve")
    public ResponseEntity<String>
    reserveStock(
            @RequestBody
            ReserveStockRequest request) {

        inventoryService.reserveStock(request);

        return ResponseEntity.ok(
                "Stock reserved successfully");
    }
    
    @PostMapping("/release")
    public ResponseEntity<String>
    releaseStock(
            @RequestBody
            ReleaseStockRequest request) {

        inventoryService.releaseStock(request);

        return ResponseEntity.ok(
                "Stock released successfully");
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponse>>
    getLowStockProducts(

            @RequestParam(
                    defaultValue = "10")
            Integer threshold) {

        return ResponseEntity.ok(
                inventoryService
                        .getLowStockProducts(
                                threshold));
    }
}