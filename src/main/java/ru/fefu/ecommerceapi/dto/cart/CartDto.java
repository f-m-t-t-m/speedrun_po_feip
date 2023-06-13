package ru.fefu.ecommerceapi.dto.cart;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private String uuid;
    private Set<CartProductCountDto> products = new LinkedHashSet<>();
    private Set<CartProductCountDto> notActualProducts = new LinkedHashSet<>();
    private Long productsCount;
    private BigDecimal orderSum;
    private BigDecimal discountSum;
    private BigDecimal orderSumWithDiscount;

}
