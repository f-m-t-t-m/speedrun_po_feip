package ru.fefu.ecommerceapi.entity;

import jakarta.annotation.security.DenyAll;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationOrderId implements Serializable {

    @Column(name = "product_variation_sku")
    private Long productVariationSku;
    @Column(name = "order_id")
    private Long orderId;

}
