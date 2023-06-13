package ru.fefu.ecommerceapi.entity;

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
public class ProductVariationCartId implements Serializable {

    @Column(name = "product_variation_sku")
    private Long productVariationSku;
    @Column(name = "cart_uuid")
    private String cartUuid;

}
