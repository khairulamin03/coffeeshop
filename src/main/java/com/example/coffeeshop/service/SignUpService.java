package com.example.coffeeshop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coffeeshop.DTO.Request.SignUpRequest;
import com.example.coffeeshop.models.entities.RoleEntity;
import com.example.coffeeshop.models.entities.Users;
import com.example.coffeeshop.models.repository.RoleRepository;
import com.example.coffeeshop.models.repository.UserRepository;

@Service
public class SignUpService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signUp(SignUpRequest request) {

        System.out.println(">>> SIGNUP SERVICE CALLED <<<");

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email sudah terdaftar");
        }

        Users user = new Users();
        user.setUsername(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        RoleEntity customerRole = roleRepository
                .findByName("CUSTOMER")
                .orElseThrow();

        user.getRoles().add(customerRole);
        userRepository.save(user);
    }
}
