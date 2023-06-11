package ru.fefu.ecommerceapi.mappers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fefu.ecommerceapi.entity.ProductVariation;
import java.util.*;

@Repository
public interface ProductVariationRepository extends CrudRepository<ProductVariation, Long>{

    Optional<ProductVariation> findBySku(Long sku);

}
