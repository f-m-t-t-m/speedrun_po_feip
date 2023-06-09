package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.fefu.ecommerceapi.dto.ProductAttributesDto;
import ru.fefu.ecommerceapi.dto.ProductCreateDto;
import ru.fefu.ecommerceapi.dto.ProductDto;
import ru.fefu.ecommerceapi.dto.enums.Color;
import ru.fefu.ecommerceapi.entity.Product;
import ru.fefu.ecommerceapi.entity.ProductAttributes;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductDto entityToDto(Product product);

    ProductAttributesDto productAttributesToDto(ProductAttributes productAttributes);

    default Map<String, Map<Color, List<ProductAttributesDto>>> productsAttributesToMap(Set<ProductAttributes> productAttributes) {
        return productAttributes.stream()
                .collect(Collectors.groupingBy(ProductAttributes::getSize,
                                               Collectors.groupingBy(ProductAttributes::getColor,
                                                       Collectors.mapping(this::productAttributesToDto, Collectors.toList()))));


    }

    @Mapping(target = "images", ignore = true)
    Product createDtoToEntity(ProductCreateDto productCreateDto);

}
