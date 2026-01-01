package com.example.coffeeshop.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coffeeshop.config.MessageStatus;
import com.example.coffeeshop.dto.request.AdminRequestCustToMerchantDTO;
import com.example.coffeeshop.dto.response.MerchantApprovalResponse;
import com.example.coffeeshop.models.entities.MerchantRequest;
import com.example.coffeeshop.models.entities.RoleEntity;
import com.example.coffeeshop.models.entities.Users;
import com.example.coffeeshop.models.enums.MerchantStatus;
import com.example.coffeeshop.models.enums.RoleName;
import com.example.coffeeshop.models.repository.MerchantRequestRepository;
import com.example.coffeeshop.models.repository.RoleRepository;
import com.example.coffeeshop.models.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminMerchantService {

        private final MerchantRequestRepository merchantRequestRepository;
        private final RoleRepository roleRepository;
        private final UserRepository userRepository;

        public MessageStatus<MerchantApprovalResponse> processMerchant(AdminRequestCustToMerchantDTO dto) {

                MerchantRequest request = merchantRequestRepository.findById(dto.getRequestId())
                                .orElseThrow(() -> new IllegalArgumentException("Merchant request not found"));

                if (request.getStatus() != MerchantStatus.PENDING) {
                        return MessageStatus.fail(
                                        "Request has been processed and than the status is "
                                                        + request.getStatus().name() + " You can't change to "
                                                        + dto.getAction(),
                                        new MerchantApprovalResponse(
                                                        request.getId(),
                                                        request.getUser().getEmail(),
                                                        request.getStatus().name()));
                }

                if ("APPROVE".equalsIgnoreCase(dto.getAction())) {
                        return approve(request);
                }

                if ("REJECT".equalsIgnoreCase(dto.getAction())) {
                        return reject(request, dto.getRejectionReason());
                }

                return MessageStatus.fail(
                                "Invalid action",
                                new MerchantApprovalResponse(
                                                request.getId(),
                                                request.getUser().getEmail(),
                                                request.getStatus().name()));
        }

        private MessageStatus<MerchantApprovalResponse> approve(MerchantRequest request) {

                request.setStatus(MerchantStatus.APPROVED);
                request.setApprovedAt(LocalDateTime.now());

                Users user = request.getUser();
                RoleEntity role = roleRepository.findByName(RoleName.MERCHANT).orElseThrow();
                user.getRoles().add(role);

                merchantRequestRepository.save(request);
                userRepository.save(user);

                return MessageStatus.success(
                                "Merchant approved successfully",
                                new MerchantApprovalResponse(
                                                request.getId(),
                                                user.getEmail(),
                                                MerchantStatus.APPROVED.name()));
        }

        private MessageStatus<MerchantApprovalResponse> reject(
                        MerchantRequest request,
                        String reason) {

                request.setStatus(MerchantStatus.REJECTED);
                request.setRejectedAt(LocalDateTime.now());
                request.setRejectionReason(reason);

                merchantRequestRepository.save(request);

                return MessageStatus.success(
                                "Merchant rejected successfully",
                                new MerchantApprovalResponse(
                                                request.getId(),
                                                request.getUser().getEmail(),
                                                MerchantStatus.REJECTED.name()));
        }

        public MessageStatus<List<Map<String, Object>>> listByStatus(MerchantStatus status) {

                List<MerchantRequest> requests = merchantRequestRepository.findByStatusOrderByUser_IdAsc(status);

                List<Map<String, Object>> result = requests.stream()
                                .map(this::toMap)
                                .toList();

                return MessageStatus.success("Merchant requests fetched", result);
        }

        public MessageStatus<List<Map<String, Object>>> listAll() {

                List<Map<String, Object>> result = merchantRequestRepository.findAll().stream()
                                .map(this::toMap)
                                .toList();

                return MessageStatus.success("All merchant requests", result);
        }

        private Map<String, Object> toMap(MerchantRequest r) {
                Map<String, Object> map = new HashMap<>();
                map.put("requestId", r.getId());
                map.put("userId", r.getUser().getId());
                map.put("email", r.getUser().getEmail());
                map.put("shopName", r.getShopName());
                map.put("status", r.getStatus());
                map.put("requestedAt", r.getCreatedAt());
                map.put("approvedAt", r.getApprovedAt());
                map.put("rejectedAt", r.getRejectedAt());
                return map;

        }
}
