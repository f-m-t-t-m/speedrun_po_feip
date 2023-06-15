package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.*;
import ru.fefu.ecommerceapi.dto.ColorDto;
import ru.fefu.ecommerceapi.entity.Color;
import ru.fefu.ecommerceapi.repository.ColorRepository;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ColorMapper {

    ColorDto entityToDto(Color color);

    Color dtoToEntity(ColorDto colorDto);

    @Mapping(target = "id", ignore = true)
    Color updateEntity(@MappingTarget Color color, ColorDto colorDto);

}
