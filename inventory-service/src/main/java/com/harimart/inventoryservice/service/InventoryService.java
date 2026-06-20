package com.harimart.inventoryservice.service;

import java.util.List;

import com.harimart.inventoryservice.dto.*;

public interface InventoryService {

    InventoryResponse createInventory(
            InventoryRequest request);

    InventoryResponse getInventoryByProductId(
            Long productId);

    InventoryResponse addStock(
            Long productId,
            StockRequest request);

    InventoryResponse reduceStock(
            Long productId,
            StockRequest request);
    
    StockAvailabilityResponse
    checkStockAvailability(Long productId);
    
    void reserveStock(
            ReserveStockRequest request);
    
    void releaseStock(
            ReleaseStockRequest request);
    
    List<InventoryResponse> getLowStockProducts(
            Integer threshold);
}