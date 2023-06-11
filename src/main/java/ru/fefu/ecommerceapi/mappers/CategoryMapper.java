package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;
import ru.fefu.ecommerceapi.dto.product.CategoryDto;
import ru.fefu.ecommerceapi.entity.Category;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDto categoryEntityToDto(Category category);

}
