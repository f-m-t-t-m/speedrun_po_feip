package ru.fefu.ecommerceapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.categories.CategoryCreateDto;
import ru.fefu.ecommerceapi.dto.categories.CategoryDto;
import ru.fefu.ecommerceapi.entity.Category;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.mappers.CategoryMapper;
import ru.fefu.ecommerceapi.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ImageService imageService;

    public List<CategoryDto> getAll() {
        return categoryRepository.getAllParentCategories().stream()
                .map(categoryMapper::categoryEntityToDto)
                .toList();
    }

    public void saveParentCategory(CategoryCreateDto categoryCreateDto) {
        Category category = categoryMapper.dtoToEntity(categoryCreateDto);
        category.setImageUrl(imageService.saveCategoryImage(categoryCreateDto));
        categoryRepository.save(category);
    }

    public void saveChildrenCategory(Long parentId, CategoryCreateDto categoryCreateDto) {
        Category parent = categoryRepository.findById(parentId).orElseThrow(NotFoundException::new);
        Category category = categoryMapper.dtoToEntity(categoryCreateDto);
        category.setImageUrl(imageService.saveCategoryImage(categoryCreateDto));
        category.setParent(parent);
        categoryRepository.save(category);
    }

    public void updateCategory(Long id, CategoryCreateDto categoryCreateDto) {
        Category category = categoryRepository.findById(id).orElseThrow(NotFoundException::new);
        category.setName(categoryCreateDto.getName());
        if (categoryCreateDto.getImage() != null) {
            category.setImageUrl(imageService.saveCategoryImage(categoryCreateDto));
        }
        categoryRepository.save(category);
    }

}
