package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.*;
import ru.fefu.ecommerceapi.dto.enums.Color;
import ru.fefu.ecommerceapi.dto.product.*;
import ru.fefu.ecommerceapi.entity.Image;
import ru.fefu.ecommerceapi.entity.Product;
import ru.fefu.ecommerceapi.entity.ProductVariation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductDto entityToDto(Product product);

    ProductAttributesDto productAttributesToDto(ProductVariation productVariation);

    default Map<String, Map<Color, List<ProductAttributesDto>>> productsAttributesToMap(Set<ProductVariation> productAttributes) {
        return productAttributes.stream()
                .collect(Collectors.groupingBy(ProductVariation::getSize,
                        Collectors.groupingBy(ProductVariation::getColor,
                                Collectors.mapping(this::productAttributesToDto, Collectors.toList()))));


    }

    default List<ShortProductDto> entityToShortDto(Product product) {
        return product.getProductVariations().stream()
                .map(productVariation -> new ShortProductDto(
                        productVariation.getSku(),
                        product.getName(),
                        productVariation.getColor(),
                        productVariation.getSize(),
                        productVariation.getStock(),
                        product.getImages().stream()
                                .filter(image -> image.getColor().equals(productVariation.getColor()))
                                .map(this::imageToDto)
                                .toList()
                ))
                .toList();
    }

    ImageDto imageToDto(Image image);

    @Mapping(target = "images", ignore = true)
    Product createDtoToEntity(ProductCreateDto productCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    void updateProduct(ProductUpdateDto productUpdateDto, @MappingTarget Product product);

}
