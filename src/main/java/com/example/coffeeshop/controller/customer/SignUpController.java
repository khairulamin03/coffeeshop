package com.example.coffeeshop.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffeeshop.DTO.Request.SignUpRequest;
import com.example.coffeeshop.config.MessageStatus;
import com.example.coffeeshop.service.SignUpService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer/signup")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping
    public ResponseEntity<MessageStatus<String>> postMethodName(@Valid @RequestBody SignUpRequest signUpRequest,
            Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageStatus.fail("Invalid request", null));
        }

        try {
            signUpService.signUp(signUpRequest);
            return ResponseEntity.ok(
                    MessageStatus.success("Sign up successful", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageStatus.fail(ex.getMessage(), null));
        }
    }
}
