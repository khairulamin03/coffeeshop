package com.example.coffeeshop.dto.request;

import jakarta.annotation.Nullable;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AdminRequestCustToMerchantDTO {
    @Id
    private Long requestId;
    @Nullable
    private String action; // "APPROVE" or "REJECT"
    @Nullable
    private String rejectionReason; // Optional, only for REJECT action
}
