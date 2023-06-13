package ru.fefu.ecommerceapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.fefu.ecommerceapi.dto.cart.CartAddDto;
import ru.fefu.ecommerceapi.entity.User;
import ru.fefu.ecommerceapi.services.AuthService;
import ru.fefu.ecommerceapi.services.CartService;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final AuthService authService;

    @GetMapping("/{cartUuid}")
    public ResponseEntity<?> getCart(@PathVariable String cartUuid, Authentication auth) {
        return ResponseEntity.ok(cartService.getCart(cartUuid, authService.getUserFromAuth(auth)));
    }

    @PutMapping("/{cartUuid}")
    public ResponseEntity<?> addProduct(@PathVariable String cartUuid, @RequestBody CartAddDto cartAddDto,
                                        Authentication auth) {
        return ResponseEntity.ok(cartService.addItem(cartUuid, cartAddDto, authService.getUserFromAuth(auth)));
    }

    @DeleteMapping("/{cartUuid}/{sku}")
    public ResponseEntity<?> deleteProduct(@PathVariable String cartUuid, @PathVariable Long sku,
                                           Authentication auth) {
        return ResponseEntity.ok(cartService.deleteProduct(cartUuid, sku, authService.getUserFromAuth(auth)));
    }

}
