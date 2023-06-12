package ru.fefu.ecommerceapi.repository;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.ProductVariation;

public interface ProductAttributesRepository extends CrudRepository<ProductVariation, Long> {

    Optional<ProductVariation> findBySku(Long sku);

}
