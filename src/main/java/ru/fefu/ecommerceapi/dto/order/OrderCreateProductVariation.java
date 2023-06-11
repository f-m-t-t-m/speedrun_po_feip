package ru.fefu.ecommerceapi.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateProductVariation {

    @NotNull
    private Long id;
    @NotNull
    private Long count;

}
