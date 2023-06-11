package ru.fefu.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_variation_order")
public class ProductVariationOrder {

    @EmbeddedId
    private ProductVariationOrderId id = new ProductVariationOrderId();
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productVariationSku")
    private ProductVariation productVariation;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;
    private Long count;

}
