package ru.fefu.ecommerceapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.Product;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value = "select p from Product p " +
            "join fetch p.productVariations pa where p.id = :id")
    Optional<Product> findByIdWithVariations(Long id);

    @Query(value = "select p from Product p " +
            "join fetch p.productVariations pa " +
            "join fetch p.images im " +
            "where pa.sku = :sku and pa.color = im.color")
    Optional<Product> findProductByVariationSku(Long sku);

}
