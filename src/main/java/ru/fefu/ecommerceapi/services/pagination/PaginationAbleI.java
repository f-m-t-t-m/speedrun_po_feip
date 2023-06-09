package ru.fefu.ecommerceapi.services.pagination;

import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;

import java.util.List;

public interface PaginationAbleI<T> {

    long count(PaginationParams paginationParams);

    List<T> getPage(PaginationParams paginationParams);

}
