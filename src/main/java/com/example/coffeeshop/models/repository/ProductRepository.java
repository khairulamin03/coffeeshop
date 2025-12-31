package com.example.coffeeshop.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coffeeshop.models.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // customer menu: tampilkan product yang available
    List<Product> findByIsAvailableTrueOrderByNameAsc();

    // customer menu: filter by category
    List<Product> findByCategoryIdAndIsAvailableTrueOrderByNameAsc(Long categoryId);

    // admin: filter by category (semua product, termasuk unavailable)
    List<Product> findByCategoryIdOrderByNameAsc(Long categoryId);
}
