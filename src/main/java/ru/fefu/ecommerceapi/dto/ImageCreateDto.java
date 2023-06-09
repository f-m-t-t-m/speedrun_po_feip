package ru.fefu.ecommerceapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.fefu.ecommerceapi.dto.enums.Color;

@Data
@NoArgsConstructor
public class ImageCreateDto {

    private Color color;
    private MultipartFile file;

}
