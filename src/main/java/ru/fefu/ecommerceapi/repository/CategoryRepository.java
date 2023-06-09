package ru.fefu.ecommerceapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query(value = "select c from Category c join fetch c.children ch")
    List<Category> getAllWithChildren();

}
