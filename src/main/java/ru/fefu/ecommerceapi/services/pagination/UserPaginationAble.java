package ru.fefu.ecommerceapi.services.pagination;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fefu.ecommerceapi.dto.ProductDto;
import ru.fefu.ecommerceapi.dto.UserDto;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;
import ru.fefu.ecommerceapi.mappers.ProductMapper;
import ru.fefu.ecommerceapi.mappers.UserMapper;
import ru.fefu.ecommerceapi.repository.CustomProductRepository;
import ru.fefu.ecommerceapi.repository.CustomUserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserPaginationAble implements PaginationAbleI<UserDto> {

    private final CustomUserRepository customUserRepository;
    private final UserMapper userMapper;

    @Override
    public long count(PaginationParams paginationParams) {
        return customUserRepository.count(paginationParams.getFilters());
    }

    @Override
    public List<UserDto> getPage(PaginationParams paginationParams) {
        return customUserRepository.findUsersByPagination(paginationParams).stream()
                .map(userMapper::entityToDto)
                .toList();
    }
}
