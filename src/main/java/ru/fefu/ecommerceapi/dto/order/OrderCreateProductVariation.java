package ru.fefu.ecommerceapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateProductVariation {

    private Long id;
    private Long count;

}
