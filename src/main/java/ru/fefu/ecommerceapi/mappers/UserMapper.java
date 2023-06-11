package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.*;
import ru.fefu.ecommerceapi.dto.auth.UserDto;
import ru.fefu.ecommerceapi.entity.User;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User dtoToEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto entityToDto(User user);

    void updateUser(UserDto userDto, @MappingTarget User user);

}
