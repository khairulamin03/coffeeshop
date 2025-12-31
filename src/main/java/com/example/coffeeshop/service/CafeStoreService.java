package com.example.coffeeshop.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.coffeeshop.DTO.Request.CafeStoreGetListCity;
import com.example.coffeeshop.DTO.Request.CafeStoreRequest;
import com.example.coffeeshop.DTO.Response.CafeStoreResponse;
import com.example.coffeeshop.models.entities.CafeStore;
import com.example.coffeeshop.models.repository.CafeStoreRepository;

import jakarta.transaction.Transactional;

@Service
public class CafeStoreService {

    @Autowired
    private CafeStoreRepository cafeStoreRepository;

    @Transactional
    public CafeStoreResponse created(CafeStoreRequest cafeStoreRequest) {
        LocalTime open = cafeStoreRequest.getOpenTime();
        LocalTime close = cafeStoreRequest.getCloseTime();

        if (open.isAfter(close)) {
            throw new IllegalArgumentException("openTime harus lebih kecil dari closeTime");
        }

        CafeStore cafeStore = new CafeStore();
        cafeStore.setName(cafeStoreRequest.getName());
        cafeStore.setAddressLine1(cafeStoreRequest.getAddressLine1());
        cafeStore.setAddressLine2(cafeStoreRequest.getAddressLine2());
        cafeStore.setCity(cafeStoreRequest.getCity());
        cafeStore.setCountry(cafeStoreRequest.getCountry());
        cafeStore.setLatitude(cafeStoreRequest.getLatitude());
        cafeStore.setLongitude(cafeStoreRequest.getLongitude());
        cafeStore.setOpenTime(open);
        cafeStore.setCloseTime(close);
        cafeStore.setIsActive(cafeStoreRequest.getIsActive() != null ? cafeStoreRequest.getIsActive() : true);

        CafeStore saved = cafeStoreRepository.save(cafeStore);

        CafeStoreResponse cafeStoreResponse = new CafeStoreResponse();
        cafeStoreResponse.setId(saved.getId());
        cafeStoreResponse.setName(saved.getName());
        cafeStoreResponse.setAddressLine1(saved.getAddressLine1());
        cafeStoreResponse.setAddressLine2(saved.getAddressLine2());
        cafeStoreResponse.setCity(saved.getCity());
        cafeStoreResponse.setCountry(saved.getCountry());
        cafeStoreResponse.setLatitude(saved.getLatitude());
        cafeStoreResponse.setLongitude(saved.getLongitude());
        cafeStoreResponse.setOpenTime(saved.getOpenTime());
        cafeStoreResponse.setCloseTime(saved.getCloseTime());
        cafeStoreResponse.setIsActive(saved.getIsActive());
        cafeStoreResponse.setCreatedAt(saved.getCreatedAt());
        return cafeStoreResponse;
    }

    @Transactional
    public List<CafeStoreResponse> getSearchByCity(CafeStoreGetListCity cafeStoreGetListCity) {
        String city = cafeStoreGetListCity.getCity();
        List<CafeStore> cafeStores = cafeStoreRepository.findByCityContaining(city);
        List<CafeStoreResponse> cafeStoreResponses = new ArrayList<>();

        for (CafeStore cafeStore : cafeStores) {
            CafeStoreResponse cafeStoreResponse = new CafeStoreResponse();
            cafeStoreResponse.setId(cafeStore.getId());
            cafeStoreResponse.setName(cafeStore.getName());
            cafeStoreResponse.setAddressLine1(cafeStore.getAddressLine1());
            cafeStoreResponse.setAddressLine2(cafeStore.getAddressLine2());
            cafeStoreResponse.setCity(cafeStore.getCity());
            cafeStoreResponse.setCountry(cafeStore.getCountry());
            cafeStoreResponse.setLatitude(cafeStore.getLatitude());
            cafeStoreResponse.setLongitude(cafeStore.getLongitude());
            cafeStoreResponse.setOpenTime(cafeStore.getOpenTime());
            cafeStoreResponse.setCloseTime(cafeStore.getCloseTime());
            cafeStoreResponse.setIsActive(cafeStore.getIsActive());
            cafeStoreResponse.setCreatedAt(cafeStore.getCreatedAt());
            cafeStoreResponses.add(cafeStoreResponse);
        }

        return cafeStoreResponses;
    }
}
