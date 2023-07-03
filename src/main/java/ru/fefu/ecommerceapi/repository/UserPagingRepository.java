package ru.fefu.ecommerceapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.fefu.ecommerceapi.entity.Order;
import ru.fefu.ecommerceapi.entity.User;

@Repository
public interface UserPagingRepository extends PagingAndSortingRepository<User, Long> {
    
}
