package com.example.coffeeshop.models.entities;

import java.time.LocalDateTime;

import com.example.coffeeshop.models.enums.MerchantStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "merchant_requests")

public class MerchantRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Users user;

    private String shopName;
    private String address;

    @Enumerated(EnumType.STRING)
    private MerchantStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;
}
