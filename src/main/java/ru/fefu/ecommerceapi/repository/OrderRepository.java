package ru.fefu.ecommerceapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fefu.ecommerceapi.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

}
