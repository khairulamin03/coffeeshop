package com.example.coffeeshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MerchantRequestDTO {
    @NotBlank
    private String shopName;

    @NotBlank
    private String address;
}
