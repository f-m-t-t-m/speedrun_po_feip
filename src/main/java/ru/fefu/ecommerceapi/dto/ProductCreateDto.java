package ru.fefu.ecommerceapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.entity.Category;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductCreateDto {

    private String name;
    private String brand;
    private String description;
    private Category category;
    private List<ProductAttributesDto> productAttributes = new ArrayList<>();
    private List<ImageCreateDto> images = new ArrayList<>();

}
