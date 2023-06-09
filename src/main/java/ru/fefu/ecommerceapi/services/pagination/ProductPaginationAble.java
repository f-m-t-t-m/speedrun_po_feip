package ru.fefu.ecommerceapi.services.pagination;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;
import ru.fefu.ecommerceapi.dto.ProductDto;
import ru.fefu.ecommerceapi.mappers.ProductMapper;
import ru.fefu.ecommerceapi.repository.CustomProductRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductPaginationAble implements PaginationAbleI<ProductDto> {

    private final CustomProductRepository customProductRepository;
    private final ProductMapper productMapper;

    @Override
    public long count(PaginationParams paginationParams) {
        return customProductRepository.countAllProducts(paginationParams.getFilters());
    }

    @Override
    public List<ProductDto> getPage(PaginationParams paginationParams) {
        return customProductRepository.findProductsByPagination(paginationParams).stream()
                .map(productMapper::entityToDto)
                .toList();
    }
}
