package ru.fefu.ecommerceapi.services.pagination;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fefu.ecommerceapi.dto.order.OrderDto;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;
import ru.fefu.ecommerceapi.mappers.OrderMapper;
import ru.fefu.ecommerceapi.repository.CustomOrderRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderPaginationAble implements PaginationAbleI<OrderDto> {

    private final CustomOrderRepository customOrderRepository;
    private final OrderMapper orderMapper;

    @Override
    public long count(PaginationParams paginationParams) {
        return customOrderRepository.count(paginationParams.getFilters());
    }

    @Override
    public List<OrderDto> getPage(PaginationParams paginationParams) {
        return customOrderRepository.findOrdersByPagination(paginationParams).stream()
                .map(orderMapper::entityToDto)
                .toList();
    }
}
