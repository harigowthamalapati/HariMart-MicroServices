package com.harimart.inventoryservice.dto;

public record ReleaseStockRequest(

        Long productId,
        Integer quantity

) {
}