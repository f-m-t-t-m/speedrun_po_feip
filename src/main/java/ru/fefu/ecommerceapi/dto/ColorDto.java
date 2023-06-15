package ru.fefu.ecommerceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorDto {

    private Long id;
    private String name;
    private String hexCode;

}
