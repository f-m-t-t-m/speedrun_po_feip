package ru.fefu.ecommerceapi.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartAddDto {

    @NotNull
    private Long Sku;
    @NotNull
    private Long count;

}
