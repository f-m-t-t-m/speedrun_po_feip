package ru.fefu.ecommerceapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;
import ru.fefu.ecommerceapi.dto.enums.Color;
import ru.fefu.ecommerceapi.entity.Product;
import ru.fefu.ecommerceapi.entity.ProductAttributes;

import java.math.BigDecimal;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class CustomProductRepository {
    @PersistenceContext
    private final EntityManager em;

    private final List<String> productAttributesFields = List.of("sku", "color", "size", "priceStart", "priceEnd");

    public long countAllProducts(Map<String, String> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Product> root = query.from(Product.class);
        List<Predicate> predicates = createProductPredicates(cb, root, filters);

        return em
                .createQuery(query.select(cb.count(root)).where(predicates.toArray(new Predicate[predicates.size() ])))
                .getSingleResult();
    }

    @Transactional
    public List<Product> findProductsByPagination(PaginationParams paginationParams) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        Root<Product> idRoot = idQuery.from(Product.class);

        if (paginationParams.getSortingBy() != null && paginationParams.getSortingOrder() != null) {
            Path<Product> path = paginationParams.getSortingBy().equals("price") ? root.get("productAttributes") : root;
            Order order;
            if (paginationParams.getSortingBy().equals("id")) {
                order = paginationParams.getSortingOrder().equals("desc") ?
                        cb.desc(path.get(paginationParams.getSortingBy())) :
                        cb.asc(path.get(paginationParams.getSortingBy()));
            } else {
                Subquery<BigDecimal> orderQuery = query.subquery(BigDecimal.class);
                Join<Product, ProductAttributes> orderJoin = orderQuery.correlate(root).join("productAttributes");
                orderQuery.select(cb.max(orderJoin.get("price")));
                order = paginationParams.getSortingOrder().equals("desc") ?
                        cb.desc(orderQuery) :
                        cb.asc(orderQuery);
            }
            query.orderBy(order);
        }

        List<Predicate> predicates = createProductPredicates(cb, idRoot, paginationParams.getFilters());
        List<Long> ids = em.createQuery(idQuery.select(idRoot.get("id")).distinct(true)
                        .where(predicates.toArray(new Predicate[ predicates.size() ])))
                        .setFirstResult((paginationParams.getCurrentPage() - 1) * paginationParams.getItemsOnPage())
                        .setMaxResults(paginationParams.getItemsOnPage())
                        .getResultList();

        System.out.println(ids);

        root.fetch("productAttributes", JoinType.INNER);
        root.fetch("images", JoinType.LEFT);

        return em.createQuery(query.select(root).where(root.get("id").in(ids)))
                .getResultList();
    }

    private List<Predicate> createProductPredicates(CriteriaBuilder cb, Root<Product> root, Map<String, String> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (var entry: filters.entrySet()) {
            if (StringUtils.isBlank(entry.getValue())) {
                continue;
            }
            if (Objects.equals(entry.getKey(), "category")) {
                List<Long> categoriesList = Arrays.stream(entry.getValue().split(",")).map(Long::valueOf).toList();
                predicates.add(root.get(entry.getKey()).get("id").in(categoriesList));
            } else if (productAttributesFields.contains(entry.getKey())) {
                predicates.add(createProductsAttributesPredicate(cb, root, entry.getKey(), entry.getValue()));
            } else {
                predicates.add(cb.like(cb.lower(root.get(entry.getKey())), "%" + entry.getValue().toLowerCase() + "%"));
            }
        }
        return predicates;
    }

    private Predicate createProductsAttributesPredicate(CriteriaBuilder cb, Root<Product> root, String key, String value) {
        if (key.equals("color")) {
            return cb.equal(root.get("productAttributes").get(key), Color.valueOf(value));
        } else {
            return cb.equal(root.get("productAttributes").get(key), value);
        }
    }

}
