package ru.fefu.ecommerceapi.dto.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PageDto<T> {

    List<T> data = new ArrayList<>();
    Integer currentPage;
    Long lastPage;
    Integer itemsOnPage;

}
