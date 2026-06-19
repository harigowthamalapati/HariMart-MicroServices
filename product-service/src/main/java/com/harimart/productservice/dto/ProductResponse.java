package com.harimart.productservice.dto;

import java.math.BigDecimal;

public record ProductResponse(

        Long id,
        String productName,
        String description,
        BigDecimal price,
        String category,
        String imageUrl,
        Boolean active

) {
}