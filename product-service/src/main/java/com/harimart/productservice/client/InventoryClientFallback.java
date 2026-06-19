package com.harimart.productservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.harimart.productservice.dto.external.InventoryRequestDto;

@Component
public class InventoryClientFallback {

    private static final Logger log =
            LoggerFactory.getLogger(
                    InventoryClientFallback.class);

    public void createInventoryFallback(
            InventoryRequestDto request,
            Throwable ex) {

        log.error(
                "Inventory Service unavailable. Product ID: {}",
                request.productId());

        log.error("Reason: {}", ex.getMessage());
    }
}