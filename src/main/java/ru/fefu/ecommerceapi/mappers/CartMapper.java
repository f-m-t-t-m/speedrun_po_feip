package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.*;
import ru.fefu.ecommerceapi.dto.cart.CartDto;
import ru.fefu.ecommerceapi.dto.cart.CartProductCountDto;
import ru.fefu.ecommerceapi.dto.product.ImageDto;
import ru.fefu.ecommerceapi.dto.product.ShortProductDto;
import ru.fefu.ecommerceapi.entity.Cart;
import ru.fefu.ecommerceapi.entity.Product;
import ru.fefu.ecommerceapi.entity.ProductVariation;
import ru.fefu.ecommerceapi.entity.ProductVariationCart;

import java.util.List;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    @Mapping(target = "notActualProducts", source = "notActualProducts")
    CartDto entityToDto(Cart cart, List<ProductVariationCart> notActualProducts);

    @Mapping(target = "product.sku", source = "productVariationCart.productVariation.sku")
    @Mapping(target = "product.name", source = "productVariationCart.productVariation.product.name")
    @Mapping(target = "product.color", source = "productVariationCart.productVariation.color")
    @Mapping(target = "product.size", source = "productVariationCart.productVariation.size")
    @Mapping(target = "product.stock", source = "productVariationCart.productVariation.stock")
    @Mapping(target = "product.images", source = "productVariationCart.productVariation", qualifiedByName = "images")
    CartProductCountDto productCartToDto(ProductVariationCart productVariationCart);

    @Named("images")
    default List<ImageDto> imageToDto(ProductVariation productVariation) {
        return productVariation.getProduct().getImages().stream()
                .filter(image -> image.getColor().equals(productVariation.getColor()))
                .map(image -> new ImageDto(image.getColor(), image.getUrl()))
                .toList();

    }

}
