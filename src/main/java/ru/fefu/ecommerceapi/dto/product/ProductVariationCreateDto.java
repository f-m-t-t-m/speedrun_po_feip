package ru.fefu.ecommerceapi.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.ColorDto;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationCreateDto {

    private Long sku;
    private Long colorId;
    private String size;
    private Long stock;
    private BigDecimal price;

}
