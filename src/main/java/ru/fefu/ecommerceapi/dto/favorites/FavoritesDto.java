package ru.fefu.ecommerceapi.dto.favorites;

import lombok.Data;
import ru.fefu.ecommerceapi.dto.product.ProductDto;

import java.util.List;

@Data
public class FavoritesDto {

    private List<ProductDto> favorites;

}
