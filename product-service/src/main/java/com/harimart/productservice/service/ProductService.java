package com.harimart.productservice.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.harimart.productservice.dto.ProductRequest;
import com.harimart.productservice.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(
            ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(
            Long id,
            ProductRequest request);

    void deleteProduct(Long id);
    
    Page<ProductResponse> getProducts(
            int page,
            int size);
    
    Page<ProductResponse> getProducts(
            int page,
            int size,
            String sortBy);
    
    List<ProductResponse> searchProducts(
            String keyword);

    List<ProductResponse> getProductsByCategory(
            String category);
}