package ru.fefu.ecommerceapi.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.ColorDto;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortProductDto {

    private Long sku;
    private String name;
    private ColorDto color;
    private String size;
    private BigDecimal price;
    private Long stock;
    private List<ImageDto> images;

}
