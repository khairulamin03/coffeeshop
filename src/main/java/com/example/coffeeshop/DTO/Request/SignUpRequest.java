package com.example.coffeeshop.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
