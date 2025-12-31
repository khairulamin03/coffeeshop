package com.example.coffeeshop.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CafeStoreGetListCity {

    @NotBlank
    private String city;

}
