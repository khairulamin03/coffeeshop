package com.example.coffeeshop.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coffeeshop.config.MessageStatus;
import com.example.coffeeshop.dto.request.MerchantRequestDTO;
import com.example.coffeeshop.models.entities.MerchantRequest;
import com.example.coffeeshop.models.entities.Users;
import com.example.coffeeshop.models.enums.MerchantStatus;
import com.example.coffeeshop.models.repository.MerchantRequestRepository;
import com.example.coffeeshop.models.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MerchantService {
        private final MerchantRequestRepository merchantRequestRepository;
        private final UserRepository userRepository;

        public MessageStatus<Map<String, Object>> requestMerchant(
                        String email,
                        MerchantRequestDTO dto) {

                Users user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                return merchantRequestRepository
                                .findByUserAndStatus(user, MerchantStatus.PENDING)
                                .map(existing -> {

                                        Map<String, Object> data = new HashMap<>();
                                        data.put("status", existing.getStatus());
                                        data.put("requestedAt", existing.getCreatedAt());

                                        return MessageStatus.fail(
                                                        "Merchant request still pending",
                                                        data);
                                })
                                .orElseGet(() -> {

                                        MerchantRequest request = new MerchantRequest();
                                        request.setUser(user);
                                        request.setShopName(dto.getShopName());
                                        request.setAddress(dto.getAddress());
                                        request.setStatus(MerchantStatus.PENDING);
                                        request.setCreatedAt(LocalDateTime.now());

                                        merchantRequestRepository.save(request);

                                        Map<String, Object> data = new HashMap<>();
                                        data.put("status", request.getStatus());
                                        data.put("requestedAt", request.getCreatedAt());

                                        return MessageStatus.success(
                                                        "Merchant request submitted successfully",
                                                        data);
                                });
        }
}
