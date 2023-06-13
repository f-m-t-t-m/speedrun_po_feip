package ru.fefu.ecommerceapi.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.enums.Color;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortProductDto {

    private Long sku;
    private String name;
    private Color color;
    private String size;
    private Long stock;
    private List<ImageDto> images;

}
