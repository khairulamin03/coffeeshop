package com.example.coffeeshop.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
