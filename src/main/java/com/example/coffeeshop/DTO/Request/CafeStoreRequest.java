package com.example.coffeeshop.dto.request;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CafeStoreRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String addressLine1;

    private String addressLine2;

    @NotBlank
    private String city;

    private String country;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(example = "08:00")
    private LocalTime openTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(example = "22:00")
    private LocalTime closeTime;

    private Boolean isActive = true;
}
