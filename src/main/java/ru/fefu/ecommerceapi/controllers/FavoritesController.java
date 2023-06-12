package ru.fefu.ecommerceapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fefu.ecommerceapi.services.FavoritesService;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getProductBySku(@PathVariable Long userId) {
        return ResponseEntity.ok(favoritesService.getFavorites(userId));
    }

    @PostMapping("/{userId}/{sku}")
    public ResponseEntity<?> addToFavorites(@PathVariable Long userId, @PathVariable Long sku) {
        favoritesService.addToFavorites(userId, sku);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{userId}/{sku}")
    public ResponseEntity<?> deleteFromFavorites(@PathVariable Long userId, @PathVariable Long sku) {
        favoritesService.deleteFromFavorites(userId, sku);
        return ResponseEntity.ok().build();
    };

}
