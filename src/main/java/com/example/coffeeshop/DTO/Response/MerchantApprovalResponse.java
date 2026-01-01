package com.example.coffeeshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantApprovalResponse {
    private Long requestId;
    private String email;
    private String status;
}
