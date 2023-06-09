package ru.fefu.ecommerceapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.ProductAttributes;

public interface ProductAttributesRepository extends CrudRepository<ProductAttributes, Long> {
}
