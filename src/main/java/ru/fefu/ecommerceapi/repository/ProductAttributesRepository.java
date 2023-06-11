package ru.fefu.ecommerceapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.ProductVariation;

public interface ProductAttributesRepository extends CrudRepository<ProductVariation, Long> {
}
