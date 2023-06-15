package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.fefu.ecommerceapi.dto.ColorDto;
import ru.fefu.ecommerceapi.dto.order.OrderDto;
import ru.fefu.ecommerceapi.dto.product.ImageDto;
import ru.fefu.ecommerceapi.dto.product.ShortProductDto;
import ru.fefu.ecommerceapi.entity.Color;
import ru.fefu.ecommerceapi.entity.Image;
import ru.fefu.ecommerceapi.entity.Order;
import ru.fefu.ecommerceapi.entity.ProductVariation;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderDto entityToDto(Order order);

    default ShortProductDto productVariationToShortDto(ProductVariation productVariation) {
        return ShortProductDto.builder()
                .color(colorToDto(productVariation.getColor()))
                .sku(productVariation.getSku())
                .stock(productVariation.getStock())
                .name(productVariation.getProduct().getName())
                .images(productVariation.getProduct().getImages().stream()
                        .filter(img -> img.getColor().getId().equals(productVariation.getColor().getId()))
                        .map(this::imageToDto)
                        .toList())
                .size(productVariation.getSize())
                .build();
    }

    ColorDto colorToDto(Color color);
    ImageDto imageToDto(Image image);
}
