package com.example.coffeeshop.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffeeshop.config.MessageStatus;
import com.example.coffeeshop.dto.request.AdminRequestCustToMerchantDTO;
import com.example.coffeeshop.dto.response.MerchantApprovalResponse;
import com.example.coffeeshop.models.enums.MerchantStatus;
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
    public ResponseEntity<MessageStatus<MerchantApprovalResponse>> process(
            @Valid @RequestBody AdminRequestCustToMerchantDTO dto) {

        return ResponseEntity.ok(
                adminMerchantService.processMerchant(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<MessageStatus<List<Map<String, Object>>>> listAll() {
        return ResponseEntity.ok(adminMerchantService.listAll());
    }

    @GetMapping("/list/pending")
    public ResponseEntity<MessageStatus<List<Map<String, Object>>>> pending() {
        return ResponseEntity.ok(
                adminMerchantService.listByStatus(MerchantStatus.PENDING));
    }

    @GetMapping("/list/approved")
    public ResponseEntity<MessageStatus<List<Map<String, Object>>>> approved() {
        return ResponseEntity.ok(
                adminMerchantService.listByStatus(MerchantStatus.APPROVED));
    }

    @GetMapping("/list/rejected")
    public ResponseEntity<MessageStatus<List<Map<String, Object>>>> rejected() {
        return ResponseEntity.ok(
                adminMerchantService.listByStatus(MerchantStatus.REJECTED));
    }
}
