package ru.fefu.ecommerceapi.dto.pagination;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationParams {

    @Builder.Default
    @Min(1)
    private Integer currentPage = 1;
    @Positive
    private Integer itemsOnPage = 10;
    @Builder.Default
    private String sortingBy = "id";
    @Builder.Default
    private String sortingOrder = "desc";
    private String paginationAbleClass;
    private Map<String, String> filters = new HashMap<>();

}

