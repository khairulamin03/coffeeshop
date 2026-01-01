package com.example.coffeeshop.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coffeeshop.config.MessageStatus;
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

        public MessageStatus<?> approveMerchant(Long requestId) {

                // Update request status
                return merchantRequestRepository.findById(requestId)
                                .map(request -> {

                                        if (request.getStatus() == MerchantStatus.APPROVED) {
                                                return MessageStatus.fail(
                                                                "Merchant request already approved", null);
                                        } else if (request.getStatus() == MerchantStatus.REJECTED) {
                                                return MessageStatus.fail("Merchant reuqest already rejected", null);
                                        }

                                        // update request
                                        request.setStatus(MerchantStatus.APPROVED);
                                        request.setApprovedAt(LocalDateTime.now());

                                        // add MERCHANT role
                                        Users user = request.getUser();
                                        RoleEntity merchantRole = roleRepository
                                                        .findByName(RoleName.MERCHANT)
                                                        .orElseThrow();

                                        user.getRoles().add(merchantRole);

                                        merchantRequestRepository.save(request);
                                        userRepository.save(user);

                                        return MessageStatus.success(
                                                        "Merchant approved successfully",
                                                        Map.of(
                                                                        "requestId", request.getId(),
                                                                        "userEmail", user.getEmail(),
                                                                        "newRole", "MERCHANT"));
                                })
                                .orElseGet(() -> MessageStatus.fail("Merchant request not found", null));
        }

        public MessageStatus<?> rejectMerchant(Long requestId, String action, String reason) {

                // Update request status
                return merchantRequestRepository.findById(requestId)
                                .map(request -> {

                                        if (request.getStatus() == MerchantStatus.REJECTED) {
                                                return MessageStatus.fail(
                                                                "Merchant request already rejected", null);

                                        }

                                        // update request
                                        request.setStatus(MerchantStatus.REJECTED);
                                        request.setRejectedAt(LocalDateTime.now());
                                        request.setRejectionReason(reason);

                                        merchantRequestRepository.save(request);

                                        return MessageStatus.success(
                                                        "Merchant rejected successfully",
                                                        Map.of(
                                                                        "requestId", request.getId(),
                                                                        "userEmail", request.getUser().getEmail(),
                                                                        "reason", reason));
                                })
                                .orElseGet(() -> MessageStatus.fail("Merchant request not found", null));
        }

        public MessageStatus<?> listAll() {

                Sort sort = Sort.by(Sort.Direction.ASC, "user.id");

                var requests = merchantRequestRepository.findAll(sort);

                return MessageStatus.success(
                                "Merchant requests fetched",
                                mapResponse(requests));
        }

        public MessageStatus<?> listByStatus(MerchantStatus status) {

                Sort sort = Sort.by(Sort.Direction.ASC, "user.id");

                var requests = merchantRequestRepository.findByStatus(status, sort);

                return MessageStatus.success(
                                "Merchant requests fetched",
                                mapResponse(requests));
        }

        private List<Map<String, Object>> mapResponse(
                        List<MerchantRequest> requests) {

                return requests.stream()
                                .map(r -> {
                                        Map<String, Object> map = new HashMap<>();

                                        map.put("requestId", r.getId());
                                        map.put("userId", r.getUser().getId());
                                        map.put("email", r.getUser().getEmail());
                                        map.put("shopName", r.getShopName());
                                        map.put("status", r.getStatus().name());
                                        map.put("requestedAt", r.getCreatedAt());

                                        // OPTIONAL FIELDS (AMAN)
                                        map.put("approvedAt", r.getApprovedAt());
                                        map.put("rejectedAt", r.getRejectedAt());

                                        return map;
                                })
                                .toList();
        }
}
