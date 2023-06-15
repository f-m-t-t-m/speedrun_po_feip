package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.*;
import ru.fefu.ecommerceapi.dto.ColorDto;
import ru.fefu.ecommerceapi.dto.product.*;
import ru.fefu.ecommerceapi.entity.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductDto entityToDto(Product product);

    ProductVariationsShortDto productAttributesToDto(ProductVariation productVariation);

    default Map<String, Map<String, List<ProductVariationsShortDto>>> productsAttributesToMap(Set<ProductVariation> productAttributes) {
        return productAttributes.stream()
                .collect(Collectors.groupingBy(ProductVariation::getSize,
                        Collectors.groupingBy(pv -> pv.getColor().getName(),
                                Collectors.mapping(this::productAttributesToDto, Collectors.toList()))));
    }

    default List<ShortProductDto> favoritesToShortDto(List<Favorites> favorites) {
        return favorites.stream()
                .map(Favorites::getProductVariation)
                .map(pv -> new ShortProductDto(
                        pv.getSku(),
                        pv.getProduct().getName(),
                        colorToDto(pv.getColor()),
                        pv.getSize(),
                        pv.getStock(),
                        pv.getProduct().getImages().stream()
                                .filter(image -> image.getColor().equals(pv.getColor()))
                                .map(this::imageToDto)
                                .toList()
                ))
                .toList();
    }

    ImageDto imageToDto(Image image);

    ColorDto colorToDto(Color color);

}
