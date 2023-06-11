package ru.fefu.ecommerceapi.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.fefu.ecommerceapi.dto.enums.Color;

@Data
@NoArgsConstructor
public class ImageCreateDto {

    @NotNull
    private Color color;
    @NotNull
    private MultipartFile file;

}
