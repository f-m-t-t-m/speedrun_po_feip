package ru.fefu.ecommerceapi.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.enums.Color;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private String description;
    private Map<String, Map<Color, List<ProductAttributesDto>>> productAttributes = new LinkedHashMap<>();
    private List<ImageDto> images = new ArrayList<>();

}
