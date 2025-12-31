package com.example.coffeeshop.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coffeeshop.models.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
