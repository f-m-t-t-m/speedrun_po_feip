package ru.fefu.ecommerceapi.repository;

import java.util.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.Favorites;
import ru.fefu.ecommerceapi.entity.FavoritesId;
import ru.fefu.ecommerceapi.entity.Product;

import java.util.List;

public interface FavoritesRepository extends CrudRepository<Favorites, FavoritesId> {

    @Query(value = "select f from Favorites f " +
            "join fetch f.productVariation pa " +
            "join fetch pa.product p " +
            "join fetch p.images im " +
            "where f.user.id = :userId " +
            "order by f.timestamp desc")
    List<Favorites> getFavoritesProductsByUserId(Long userId);

    void deleteByUserIdAndProductVariationSku(Long userId, Long sku);

    Optional<Favorites> findByUserIdAndProductVariationSku(Long userId, Long sku);

}
