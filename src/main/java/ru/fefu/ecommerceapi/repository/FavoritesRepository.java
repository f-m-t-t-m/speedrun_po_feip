package ru.fefu.ecommerceapi.repository;

import java.util.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.Favorites;
import ru.fefu.ecommerceapi.entity.FavoritesId;
import ru.fefu.ecommerceapi.entity.Product;

import java.util.List;

public interface FavoritesRepository extends CrudRepository<Favorites, FavoritesId> {

    @Query(value = "select p from Favorites f " +
            "join f.productVariation pa " +
            "join pa.product p " +
            "join fetch p.productVariations pa1 " +
            "join fetch p.images im " +
            "where pa.sku = pa1.sku and f.user.id = :userId")
    List<Product> getFavoritesProductsByUserId(Long userId);

    void deleteByUserIdAndProductVariationSku(Long userId, Long sku);

    Optional<Favorites> findByUserIdAndProductVariationSku(Long userId, Long sku);

}
