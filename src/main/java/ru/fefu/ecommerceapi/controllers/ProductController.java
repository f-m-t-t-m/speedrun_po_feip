package ru.fefu.ecommerceapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fefu.ecommerceapi.dto.ProductCreateDto;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;
import ru.fefu.ecommerceapi.services.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts(PaginationParams paginationParams) {
        paginationParams.setPaginationAbleClass("productPaginationAble");
        return ResponseEntity.ok().body(productService.getPageDto(paginationParams));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@ModelAttribute ProductCreateDto productCreateDto) {
        productService.saveProduct(productCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
