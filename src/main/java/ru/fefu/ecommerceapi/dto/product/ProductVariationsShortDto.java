package ru.fefu.ecommerceapi.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductVariationsShortDto {

    private Long sku;
    private Integer stock;
    private BigDecimal price;

}
