package com.example.coffeeshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffeeshop.DTO.Request.CafeStoreGetListCity;
import com.example.coffeeshop.DTO.Request.CafeStoreRequest;
import com.example.coffeeshop.DTO.Response.CafeStoreResponse;
import com.example.coffeeshop.config.MessageStatus;
import com.example.coffeeshop.service.CafeStoreService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cafes")
public class CofeShopController {

    @Autowired
    private CafeStoreService cafeStoreService;

    @PostMapping("/created")
    public ResponseEntity<MessageStatus<CafeStoreResponse>> create(
            @Valid @RequestBody CafeStoreRequest cafeStoreRequest, Errors errors) {
        if (errors.hasErrors() && cafeStoreRequest.getName().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MessageStatus.fail("Validation errors", null));
        } else {

            CafeStoreResponse cafeStoreResponse = cafeStoreService.created(cafeStoreRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(MessageStatus.success("Sukses", cafeStoreResponse));
        }

    }

    @GetMapping("/getlistcofestore")
    public ResponseEntity<MessageStatus<List<CafeStoreResponse>>> getSearchByCity(
            @Valid @RequestBody CafeStoreGetListCity cafeStoreGetListCity, Errors errors) {

        List<CafeStoreResponse> cafeStoreResponses = cafeStoreService.getSearchByCity(cafeStoreGetListCity);
        if (cafeStoreResponses.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(MessageStatus.fail("No cafe stores found", null));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageStatus.success("Success", cafeStoreResponses));
    }

}
