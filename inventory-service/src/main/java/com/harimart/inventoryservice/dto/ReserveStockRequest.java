package com.harimart.inventoryservice.dto;

public record ReserveStockRequest(

        Long productId,
        Integer quantity

) {
}