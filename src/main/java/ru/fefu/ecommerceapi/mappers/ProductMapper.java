package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.*;
import ru.fefu.ecommerceapi.dto.enums.Color;
import ru.fefu.ecommerceapi.dto.product.ProductAttributesDto;
import ru.fefu.ecommerceapi.dto.product.ProductCreateDto;
import ru.fefu.ecommerceapi.dto.product.ProductDto;
import ru.fefu.ecommerceapi.dto.product.ProductUpdateDto;
import ru.fefu.ecommerceapi.entity.Product;
import ru.fefu.ecommerceapi.entity.ProductVariation;

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

    @Mapping(target = "images", ignore = true)
    Product createDtoToEntity(ProductCreateDto productCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    void updateProduct(ProductUpdateDto productUpdateDto, @MappingTarget Product product);

}
