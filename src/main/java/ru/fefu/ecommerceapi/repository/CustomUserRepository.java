package ru.fefu.ecommerceapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import ru.fefu.ecommerceapi.dto.enums.Color;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;
import ru.fefu.ecommerceapi.entity.Product;
import ru.fefu.ecommerceapi.entity.ProductAttributes;
import ru.fefu.ecommerceapi.entity.User;

import java.math.BigDecimal;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class CustomUserRepository {
    @PersistenceContext
    private final EntityManager em;

    public long count(Map<String, String> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        List<Predicate> predicates = createPredicates(cb, root, filters);

        return em
                .createQuery(query.select(cb.count(root)).where(predicates.toArray(new Predicate[predicates.size() ])))
                .getSingleResult();
    }

    @Transactional
    public List<User> findUsersByPagination(PaginationParams paginationParams) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        List<Predicate> predicates = createPredicates(cb, root, paginationParams.getFilters());
        if (paginationParams.getSortingBy() != null && paginationParams.getSortingOrder() != null) {
            Order order = paginationParams.getSortingOrder().equals("desc") ?
                    cb.desc(root.get(paginationParams.getSortingBy())) :
                    cb.asc(root.get(paginationParams.getSortingBy()));
            query.orderBy(order);
        }
        return em.createQuery(query.select(root).where(predicates.toArray(new Predicate[predicates.size() ])))
                .getResultList();
    }

    private List<Predicate> createPredicates(CriteriaBuilder cb, Root<User> root, Map<String, String> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (var entry: filters.entrySet()) {
            if (StringUtils.isBlank(entry.getValue())) {
                continue;
            }
            predicates.add(cb.like(cb.lower(root.get(entry.getKey())), "%" + entry.getValue().toLowerCase() + "%"));
        }
        return predicates;
    }

}
