package ru.fefu.ecommerceapi.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.enums.Color;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private Color color;
    private String url;

}
