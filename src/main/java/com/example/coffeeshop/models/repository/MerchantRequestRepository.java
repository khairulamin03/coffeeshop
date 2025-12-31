package com.example.coffeeshop.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coffeeshop.models.entities.MerchantRequest;
import com.example.coffeeshop.models.entities.Users;
import com.example.coffeeshop.models.enums.MerchantStatus;

public interface MerchantRequestRepository extends JpaRepository<MerchantRequest, Long> {
    Optional<MerchantRequest> findByUserAndStatus(Users user, MerchantStatus status);
}
