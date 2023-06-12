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
public class Favorites {

    @EmbeddedId
    private FavoritesId favoritesId = new FavoritesId();
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productVariationSku")
    private ProductVariation productVariation;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

}
