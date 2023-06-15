package ru.fefu.ecommerceapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.fefu.ecommerceapi.services.AuthService;
import ru.fefu.ecommerceapi.services.FavoritesService;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;
    private final AuthService authService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getFavorites(Authentication auth) {
        return ResponseEntity.ok(favoritesService.getFavorites(authService.getUserFromAuth(auth)));
    }

    @GetMapping("/{sku}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> isInFavorites(@PathVariable Long sku, Authentication auth) {
        return ResponseEntity.ok(favoritesService.isProductInFavorites(sku, authService.getUserFromAuth(auth)));
    }

    @PostMapping("/{sku}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addToFavorites(@PathVariable Long sku, Authentication auth) {
        favoritesService.addToFavorites(sku, authService.getUserFromAuth(auth));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{sku}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteFromFavorites(@PathVariable Long sku, Authentication auth) {
        favoritesService.deleteFromFavorites(sku, authService.getUserFromAuth(auth));
        return ResponseEntity.ok().build();
    };

}
