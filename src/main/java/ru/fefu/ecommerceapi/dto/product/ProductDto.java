package ru.fefu.ecommerceapi.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Map<String, Map<String, List<ProductVariationsShortDto>>> productVariations = new LinkedHashMap<>();
    private List<ImageDto> images = new ArrayList<>();

}
