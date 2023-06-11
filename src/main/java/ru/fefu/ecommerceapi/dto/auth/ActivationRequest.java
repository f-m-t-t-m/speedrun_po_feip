package ru.fefu.ecommerceapi.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivationRequest {

    @NotBlank
    private String phone;
    @NotBlank
    private String code;

}
