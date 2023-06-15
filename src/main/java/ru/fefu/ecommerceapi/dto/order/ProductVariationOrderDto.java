package ru.fefu.ecommerceapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.product.ProductVariationsShortDto;
import ru.fefu.ecommerceapi.dto.product.ShortProductDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationOrderDto {

    private ShortProductDto productVariation;
    private Long count;

}
