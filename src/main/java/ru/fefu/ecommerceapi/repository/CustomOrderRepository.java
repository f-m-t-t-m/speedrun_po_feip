package ru.fefu.ecommerceapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomOrderRepository {
    @PersistenceContext
    private final EntityManager em;

    public long count(Map<String, String> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<ru.fefu.ecommerceapi.entity.Order> root = query.from(ru.fefu.ecommerceapi.entity.Order.class);

        return em
                .createQuery(query.select(cb.count(root)))
                .getSingleResult();
    }

    public List<ru.fefu.ecommerceapi.entity.Order> findOrdersByPagination(PaginationParams paginationParams) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ru.fefu.ecommerceapi.entity.Order> query = cb.createQuery(ru.fefu.ecommerceapi.entity.Order.class);
        Root<ru.fefu.ecommerceapi.entity.Order> root = query.from(ru.fefu.ecommerceapi.entity.Order.class);

        CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        Root<ru.fefu.ecommerceapi.entity.Order> idRoot = idQuery.from(ru.fefu.ecommerceapi.entity.Order.class);
        List<Long> ids = em.createQuery(idQuery.select(idRoot.get("id")).distinct(true))
                .setFirstResult((paginationParams.getCurrentPage() - 1) * paginationParams.getItemsOnPage())
                .setMaxResults(paginationParams.getItemsOnPage())
                .getResultList();

        root.fetch("address", JoinType.LEFT);
        root.fetch("productVariations", JoinType.LEFT).fetch("productVariation");

        if (paginationParams.getSortingBy() != null && paginationParams.getSortingOrder() != null) {
            Order order = paginationParams.getSortingOrder().equals("desc") ?
                    cb.desc(root.get(paginationParams.getSortingBy())) :
                    cb.asc(root.get(paginationParams.getSortingBy()));
            query.orderBy(order);
        }
        return em.createQuery(query.select(root)
                        .where(root.get("id").in(ids)))
                .getResultList();
    }

}
