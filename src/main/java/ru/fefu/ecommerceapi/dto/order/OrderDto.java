package ru.fefu.ecommerceapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.enums.ReceiveType;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private String name;
    private String email;
    private ReceiveType receiveType;
    private String comment;
    private List<ProductVariationOrderDto> productVariations = new ArrayList<>();
    private AddressDto address;

}
