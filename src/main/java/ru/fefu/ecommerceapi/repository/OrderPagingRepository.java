package ru.fefu.ecommerceapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.fefu.ecommerceapi.entity.Order;

@Repository
public interface OrderPagingRepository extends PagingAndSortingRepository<Order, Long> {
    
}
