package com.harimart.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.harimart.productservice.dto.external.InventoryRequestDto;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @PostMapping("/HMart/v1/inventory")
    void createInventory(
            @RequestBody
            InventoryRequestDto request);
}