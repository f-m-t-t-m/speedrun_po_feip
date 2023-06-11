package ru.fefu.ecommerceapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.Product;

import java.util.*;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value = "select p from Product p join fetch p.productAttributes pa order by p.id desc ")
    List<Product> findWithAttributes();

    @Query(value = "select p from Product p join fetch p.productAttributes pa where p.id = :id")
    Optional<Product> findByIdWithVariations(Long id);

}
