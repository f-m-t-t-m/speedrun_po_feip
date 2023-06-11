package ru.fefu.ecommerceapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fefu.ecommerceapi.entity.ProductVariationOrder;
import ru.fefu.ecommerceapi.entity.ProductVariationOrderId;

@Repository
public interface ProductVariationOrderRepository extends CrudRepository<ProductVariationOrder, ProductVariationOrderId> {
}
