package ru.fefu.ecommerceapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;
import ru.fefu.ecommerceapi.dto.order.OrderDto;
import ru.fefu.ecommerceapi.entity.Order;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderDto entityToDto(Order order);

}
