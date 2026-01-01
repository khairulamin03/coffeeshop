package com.example.coffeeshop.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coffeeshop.config.MessageStatus;
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
}
