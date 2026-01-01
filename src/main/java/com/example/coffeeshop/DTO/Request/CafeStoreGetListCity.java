package com.example.coffeeshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CafeStoreGetListCity {

    @NotBlank
    private String city;

}
