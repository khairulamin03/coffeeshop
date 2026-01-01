package com.example.coffeeshop.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffeeshop.DTO.Request.AdminRequestCustToMerchantDTO;
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
    public ResponseEntity<?> approve(@Valid @RequestBody AdminRequestCustToMerchantDTO adminRequestCustToMerchantDTO) {

        if (adminRequestCustToMerchantDTO.getRequestId() != null
                && adminRequestCustToMerchantDTO.getAction().isBlank() == true
                && adminRequestCustToMerchantDTO.getRejectionReason().isBlank() == true) {
            return ResponseEntity
                    .ok(adminMerchantService.approveMerchant(adminRequestCustToMerchantDTO.getRequestId()));
        } else if (adminRequestCustToMerchantDTO.getRequestId() != null
                && adminRequestCustToMerchantDTO.getAction().isBlank() == false
                && adminRequestCustToMerchantDTO.getRejectionReason().isBlank() == false) {

            return ResponseEntity
                    .ok(adminMerchantService.rejectMerchant(adminRequestCustToMerchantDTO.getRequestId(),
                            adminRequestCustToMerchantDTO.getAction(),
                            adminRequestCustToMerchantDTO.getRejectionReason()));
        }

        return ResponseEntity.badRequest().body("Invalid action. Must be 'APPROVE' or 'REJECT'.");
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(
                adminMerchantService.listAll());
    }

    @GetMapping("/list/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> pending() {
        return ResponseEntity.ok(
                adminMerchantService.listByStatus(MerchantStatus.PENDING));
    }

    @GetMapping("/list/approved")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approved() {
        return ResponseEntity.ok(
                adminMerchantService.listByStatus(MerchantStatus.APPROVED));
    }

    @GetMapping("/list/rejected")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejected() {
        return ResponseEntity.ok(
                adminMerchantService.listByStatus(MerchantStatus.REJECTED));
    }

}
