package ru.fefu.ecommerceapi.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;
    private List<CategoryDto> children = new ArrayList<>();

}
