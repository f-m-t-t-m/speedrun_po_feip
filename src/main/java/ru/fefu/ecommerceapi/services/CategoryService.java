package ru.fefu.ecommerceapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.product.CategoryDto;
import ru.fefu.ecommerceapi.mappers.CategoryMapper;
import ru.fefu.ecommerceapi.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAll() {
        return categoryRepository.getAllWithChildren().stream()
                .map(categoryMapper::categoryEntityToDto)
                .toList();
    }

}
