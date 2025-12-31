package com.example.coffeeshop.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffeeshop.DTO.Request.LoginRequest;
import com.example.coffeeshop.config.MessageStatus;
import com.example.coffeeshop.security.CustomUserDetails;
import com.example.coffeeshop.service.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<MessageStatus<String>> login(
            @Valid @RequestBody LoginRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

            String token = jwtService.generateToken(user);

            // login berhasil
            return ResponseEntity.ok(
                    MessageStatus.success("Login successful", token));

        } catch (BadCredentialsException ex) {

            // password salah ATAU user tidak ada
            return ResponseEntity
                    .status(401)
                    .body(MessageStatus.fail("Email atau password salah", null));

        } catch (AuthenticationException ex) {

            // error auth lain
            return ResponseEntity
                    .status(401)
                    .body(MessageStatus.fail("Authentication failed", null));
        }
    }

}