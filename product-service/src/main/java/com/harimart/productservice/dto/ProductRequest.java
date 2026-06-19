package com.harimart.productservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(

        @NotBlank
        String productName,

        String description,

        @NotNull
        @DecimalMin("0.0")
        BigDecimal price,

        @NotBlank
        String category,

        String imageUrl

) {
}