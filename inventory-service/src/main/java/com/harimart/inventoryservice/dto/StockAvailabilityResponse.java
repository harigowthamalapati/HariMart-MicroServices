package com.harimart.inventoryservice.dto;

public record StockAvailabilityResponse(

        Long productId,
        Integer availableQuantity,
        boolean inStock

) {
}