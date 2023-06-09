package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.fefu.ecommerceapi.dto.UserDto;
import ru.fefu.ecommerceapi.entity.User;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User dtoToEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto entityToDto(User user);

}
