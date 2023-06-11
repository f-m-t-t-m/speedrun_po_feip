package ru.fefu.ecommerceapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.enums.Color;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationDto {

    private Long sku;
    private Color color;
    private String size;
    private Long stock;
    private BigDecimal price;

}
