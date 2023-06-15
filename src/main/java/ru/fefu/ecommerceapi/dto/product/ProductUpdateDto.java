package ru.fefu.ecommerceapi.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductUpdateDto {

    @NotBlank
    private String name;
    @NotBlank
    private String brand;
    private String description;
    @NotNull
    private Long categoryId;
    private List<ProductVariationCreateDto> productVariations = new ArrayList<>();
    private List<ImageCreateDto> imagesToAdd = new ArrayList<>();
    private List<String> imageUrlsToDelete = new ArrayList<>();

}
