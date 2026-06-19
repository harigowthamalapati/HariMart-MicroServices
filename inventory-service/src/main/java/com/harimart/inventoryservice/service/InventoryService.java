package com.harimart.inventoryservice.service;

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
}