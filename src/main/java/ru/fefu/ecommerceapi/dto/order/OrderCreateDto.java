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
public class OrderCreateDto {

    private String name;
    private String email;
    private ReceiveType receiveType;
    private AddressDto address;
    private String comment;
    private List<OrderCreateProductVariation> productVariation = new ArrayList<>();

}
