package ru.fefu.ecommerceapi.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.ColorDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private ColorDto color;
    private String url;

}
