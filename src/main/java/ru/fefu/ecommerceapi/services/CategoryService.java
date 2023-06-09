package ru.fefu.ecommerceapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.CategoryDto;
import ru.fefu.ecommerceapi.entity.Category;
import ru.fefu.ecommerceapi.mappers.CategoryMapper;
import ru.fefu.ecommerceapi.repository.CategoryRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAll() {
        return categoryRepository.getAllWithChildren().stream()
                .map(categoryMapper::categoryEntityToDto)
                .toList();
    }

}
