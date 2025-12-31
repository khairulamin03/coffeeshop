package com.example.coffeeshop.controller.customer;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffeeshop.DTO.Request.MerchantRequestDTO;
import com.example.coffeeshop.config.MessageStatus;
import com.example.coffeeshop.security.CustomUserDetails;
import com.example.coffeeshop.service.MerchantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @PostMapping("/request")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<MessageStatus<Object>> requestMerchant(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody MerchantRequestDTO dto,
            Errors errors) {

        // 1️⃣ Validation error
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(MessageStatus.fail(
                            "Invalid request data",
                            errors.getAllErrors()));
        }

        // 2️⃣ Business logic
        MessageStatus<Map<String, Object>> serviceResponse = merchantService.requestMerchant(
                user.getUsername(), dto);

        // 3️⃣ Return standardized response
        return ResponseEntity.ok(
                MessageStatus.fail(
                        serviceResponse.getMessage(),
                        serviceResponse.getData()));
    }
}
