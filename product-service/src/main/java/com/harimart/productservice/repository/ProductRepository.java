package com.harimart.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harimart.productservice.entity.Product;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findByProductNameContainingIgnoreCase(
            String keyword);
}