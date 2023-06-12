package ru.fefu.ecommerceapi.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FavoritesId implements Serializable {

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "product_variation_sku")
    private Long productVariationSku;

}
