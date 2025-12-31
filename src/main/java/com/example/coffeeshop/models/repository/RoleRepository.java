package com.example.coffeeshop.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coffeeshop.models.entities.RoleEntity;
import com.example.coffeeshop.models.enums.RoleName;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleName name);
}
