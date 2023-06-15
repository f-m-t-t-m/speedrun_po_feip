package ru.fefu.ecommerceapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.Category;

import java.util.*;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query(value = "select c from Category c " +
            "left join fetch c.children ch " +
            "where c.parent is null " +
            "order by c.id asc")
    List<Category> getAllParentCategories();

    @Query(value = "select c from Category c " +
                    "where c.id = :id")
    Optional<Category> findById(Long id);

    @Query(value = "delete from Category c " +
                    "where c.parent.id = :id")
    void deleteChildrenById(Long id);

}
