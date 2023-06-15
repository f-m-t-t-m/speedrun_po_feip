package ru.fefu.ecommerceapi.dto.categories;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CategoryCreateDto {

    private String name;
    private MultipartFile image;

}
