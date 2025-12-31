package com.example.coffeeshop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coffeeshop.models.entities.MerchantRequest;
import com.example.coffeeshop.models.entities.RoleEntity;
import com.example.coffeeshop.models.enums.MerchantStatus;
import com.example.coffeeshop.models.enums.RoleName;
import com.example.coffeeshop.models.repository.MerchantRequestRepository;
import com.example.coffeeshop.models.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminMerchantService {
    private final MerchantRequestRepository merchantRequestRepository;
    private final RoleRepository roleRepository;

    public void approveMerchant(Long requestId) {

        MerchantRequest request = merchantRequestRepository.findById(requestId)
                .orElseThrow();

        request.setStatus(MerchantStatus.APPROVED);

        RoleEntity merchantRole = roleRepository.findByName(RoleName.MERCHANT)
                .orElseThrow();

        request.getUser()
                .getRoles()
                .add(merchantRole);
    }
}
