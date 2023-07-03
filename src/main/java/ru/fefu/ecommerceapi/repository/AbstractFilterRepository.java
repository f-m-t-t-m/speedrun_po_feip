package ru.fefu.ecommerceapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.core.GenericTypeResolver;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;
import ru.fefu.ecommerceapi.entity.Product;

import java.util.List;
import java.util.Map;

public abstract class AbstractFilterRepository<T> {

    @PersistenceContext
    protected EntityManager em;

    protected void setEm(EntityManager em) {
        this.em = em;
    }

    public long count(Map<String, String> filters) {
        Class<T> clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractFilterRepository.class);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> root = query.from(clazz);
        List<Predicate> predicates = createPredicates(cb, root, filters);

        return em
                .createQuery(query.select(cb.count(root)).where(predicates.toArray(new Predicate[0])))
                .getSingleResult();
    }

    abstract protected List<T> findByFilters(PaginationParams paginationParams);
    abstract protected void setOrder(CriteriaBuilder cb, Root<T> root, CriteriaQuery<Product> query,
                           String sortingBy, String sortingOrder);
    abstract protected List<Predicate> createPredicates(CriteriaBuilder builder, Root<T> root, Map<String, String> filters);

}
