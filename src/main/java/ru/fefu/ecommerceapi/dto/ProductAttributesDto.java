package ru.fefu.ecommerceapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.enums.Color;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductAttributesDto {

    private int stock;
    private BigDecimal price;

}
