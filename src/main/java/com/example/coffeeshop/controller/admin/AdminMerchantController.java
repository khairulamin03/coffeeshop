package com.example.coffeeshop.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffeeshop.service.AdminMerchantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/merchant")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminMerchantController {
    private final AdminMerchantService service;

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        service.approveMerchant(id);
        return ResponseEntity.ok("Approved");
    }
}
