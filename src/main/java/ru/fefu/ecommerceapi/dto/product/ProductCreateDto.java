package ru.fefu.ecommerceapi.dto.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.order.ProductVariationDto;
import ru.fefu.ecommerceapi.entity.Category;

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
    private Category category;
    private List<ProductVariationDto> productAttributes = new ArrayList<>();
    private List<ImageCreateDto> images = new ArrayList<>();

}
