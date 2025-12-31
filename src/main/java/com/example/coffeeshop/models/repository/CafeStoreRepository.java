package com.example.coffeeshop.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coffeeshop.models.entities.CafeStore;

public interface CafeStoreRepository extends JpaRepository<CafeStore, Long> {
    List<CafeStore> findByName(String name);

    List<CafeStore> findByCountry(String country);

    List<CafeStore> findByCityContaining(String city);
}
