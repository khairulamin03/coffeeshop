package com.example.coffeeshop.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class CafeStoreResponse {
    private Long id;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
