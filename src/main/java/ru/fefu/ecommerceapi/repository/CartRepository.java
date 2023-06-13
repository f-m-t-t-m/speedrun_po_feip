package ru.fefu.ecommerceapi.repository;

import java.util.*;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "graph.Cart")
    Optional<Cart> findCartByUuid(String uuid);

}
