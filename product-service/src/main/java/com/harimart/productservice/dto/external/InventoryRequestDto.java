package com.harimart.productservice.dto.external;

public record InventoryRequestDto(

        Long productId,
        Integer availableQuantity,
        String warehouseLocation

) {
}