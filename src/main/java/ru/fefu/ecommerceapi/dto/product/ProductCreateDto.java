package ru.fefu.ecommerceapi.dto.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductCreateDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String brand;
    private String description;
    @NotNull
    private Long categoryId;
    private List<ProductVariationCreateDto> productVariations = new ArrayList<>();
    private List<ImageCreateDto> images = new ArrayList<>();

}
