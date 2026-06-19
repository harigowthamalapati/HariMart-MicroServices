package com.harimart.productservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.harimart.productservice.client.InventoryClient;
import com.harimart.productservice.client.InventoryClientFallback;
import com.harimart.productservice.dto.ProductRequest;
import com.harimart.productservice.dto.ProductResponse;
import com.harimart.productservice.dto.external.InventoryRequestDto;
import com.harimart.productservice.entity.Product;
import com.harimart.productservice.exception.ResourceNotFoundException;
import com.harimart.productservice.repository.ProductRepository;
import com.harimart.productservice.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient;
    private final InventoryClientFallback inventoryFallback;

    @Override
    @CircuitBreaker(
            name = "inventoryService",
            fallbackMethod = "inventoryFallback")
    public ProductResponse createProduct(
            ProductRequest request) {

        Product product = Product.builder()
                .productName(request.productName())
                .description(request.description())
                .price(request.price())
                .category(request.category())
                .imageUrl(request.imageUrl())
                .active(true)
                .build();

        Product savedProduct =
                productRepository.save(product);

        inventoryClient.createInventory(
                new InventoryRequestDto(
                        savedProduct.getId(),
                        0,
                        "Default Warehouse"));

        return mapToResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id));

        return mapToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(
            Long id,
            ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id));

        product.setProductName(request.productName());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setCategory(request.category());
        product.setImageUrl(request.imageUrl());

        Product updatedProduct =
                productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id));

        productRepository.delete(product);
    }

    private ProductResponse mapToResponse(
            Product product) {

        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getImageUrl(),
                product.getActive()
        );
    }
    
    public ProductResponse inventoryFallback(
            ProductRequest request,
            Throwable ex) {

        throw new RuntimeException(
                "Product created but Inventory Service unavailable");
    }
}