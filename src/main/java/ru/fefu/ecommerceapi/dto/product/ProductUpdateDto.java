package ru.fefu.ecommerceapi.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.order.ProductVariationDto;
import ru.fefu.ecommerceapi.entity.Category;

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
    private Category category;
    private List<ProductVariationDto> productAttributes = new ArrayList<>();
    private List<ImageCreateDto> imagesToAdd = new ArrayList<>();
    private List<ImageDto> imagesToDelete = new ArrayList<>();

}
