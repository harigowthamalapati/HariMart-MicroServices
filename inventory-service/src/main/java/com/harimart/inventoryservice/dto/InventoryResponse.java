package com.harimart.inventoryservice.dto;

public record InventoryResponse(

        Long id,
        Long productId,
        Integer availableQuantity,
        Integer reservedQuantity,
        String warehouseLocation

) {
}