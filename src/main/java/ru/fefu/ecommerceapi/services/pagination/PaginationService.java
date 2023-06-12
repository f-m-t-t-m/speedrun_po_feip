package ru.fefu.ecommerceapi.services.pagination;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.fefu.ecommerceapi.dto.pagination.PageDto;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;

import java.util.Map;

@Service
@Validated
public class PaginationService<T> {

    private Map<String, PaginationAbleI<?>> paginationAbleMap;

    @Autowired
    public void setPaginationAbleMap(Map<String, PaginationAbleI<?>> paginationAbleMap) {
        this.paginationAbleMap = paginationAbleMap;
    }

    public PageDto<T> getPageDto(@Valid PaginationParams paginationParams) {
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setCurrentPage(paginationParams.getCurrentPage());
        pageDto.setItemsOnPage(paginationParams.getItemsOnPage());
        PaginationAbleI<T> paginationAble =
                (PaginationAbleI<T>) paginationAbleMap.get(paginationParams.getPaginationAbleClass());
        long count = paginationAble.count(paginationParams);
        pageDto.setLastPage((count + paginationParams.getItemsOnPage() - 1) / paginationParams.getItemsOnPage());
        pageDto.setData(paginationAble.getPage(paginationParams));

        return pageDto;
    }

}
