package ru.fefu.ecommerceapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fefu.ecommerceapi.dto.categories.CategoryCreateDto;
import ru.fefu.ecommerceapi.dto.categories.CategoryDto;
import ru.fefu.ecommerceapi.services.CategoryService;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok().body(categoryService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> saveCategory(@ModelAttribute CategoryCreateDto categoryDto) {
        categoryService.saveParentCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> saveCategory(@PathVariable Long id, @ModelAttribute CategoryCreateDto categoryDto) {
        categoryService.saveChildrenCategory(id, categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @ModelAttribute CategoryCreateDto categoryDto) {
        categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
