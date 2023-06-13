package ru.fefu.ecommerceapi.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.product.ShortProductDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductCountDto {

    private ShortProductDto product;
    private Long count;

}
