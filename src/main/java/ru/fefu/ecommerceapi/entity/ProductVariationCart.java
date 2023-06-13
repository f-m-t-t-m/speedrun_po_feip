package ru.fefu.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart-product")
public class ProductVariationCart {

    @EmbeddedId
    private ProductVariationCartId id = new ProductVariationCartId();
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productVariationSku")
    private ProductVariation productVariation;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cartUuid")
    private Cart cart;
    private Long count;

}
