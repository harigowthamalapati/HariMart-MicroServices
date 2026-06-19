package com.harimart.productservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.harimart.productservice.dto.ProductRequest;
import com.harimart.productservice.dto.ProductResponse;
import com.harimart.productservice.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/HMart/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity.ok(
                productService.createProduct(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>>
    getAllProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse>
    getProductById(@PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse>
    updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                "Product deleted successfully");
    }
}