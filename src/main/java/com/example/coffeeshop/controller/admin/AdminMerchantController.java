package com.example.coffeeshop.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffeeshop.DTO.Request.AdminRequestCustToMerchantDTO;
import com.example.coffeeshop.service.AdminMerchantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/merchant")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminMerchantController {
    private final AdminMerchantService adminMerchantService;

    @PostMapping("/approve")
    public ResponseEntity<?> approve(@Valid @RequestBody AdminRequestCustToMerchantDTO adminRequestCustToMerchantDTO) {

        return ResponseEntity.ok(adminMerchantService.approveMerchant(adminRequestCustToMerchantDTO.getRequestId()));
    }
}
