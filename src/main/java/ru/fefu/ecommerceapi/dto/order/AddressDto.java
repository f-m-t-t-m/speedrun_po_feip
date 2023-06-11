package ru.fefu.ecommerceapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private String city;
    private String index;
    private String street;
    private String house;
    private String flat;

}
