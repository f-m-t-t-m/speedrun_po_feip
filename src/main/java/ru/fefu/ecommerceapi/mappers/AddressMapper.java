package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;
import ru.fefu.ecommerceapi.dto.order.AddressDto;
import ru.fefu.ecommerceapi.entity.Address;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    Address dtoToEntity(AddressDto addressDto);

}
