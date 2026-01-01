package com.example.coffeeshop.DTO.Request;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AdminRequestCustToMerchantDTO {
    @Id
    private Long requestId;
}
