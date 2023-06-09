package ru.fefu.ecommerceapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.enums.Color;

@Data
@NoArgsConstructor
public class ImageDto {

    private Color color;
    private String url;

}
