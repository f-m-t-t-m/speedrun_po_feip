package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.*;
import ru.fefu.ecommerceapi.dto.categories.CategoryCreateDto;
import ru.fefu.ecommerceapi.dto.categories.CategoryDto;
import ru.fefu.ecommerceapi.entity.Category;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDto categoryEntityToDto(Category category);
    Category dtoToEntity(CategoryCreateDto categoryDto);

    Category updateCategory(@MappingTarget Category category, CategoryDto categoryDto);

}
