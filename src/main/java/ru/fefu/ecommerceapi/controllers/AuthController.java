package ru.fefu.ecommerceapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fefu.ecommerceapi.dto.auth.ActivationRequest;
import ru.fefu.ecommerceapi.dto.auth.RefreshRequest;
import ru.fefu.ecommerceapi.dto.auth.UserDto;
import ru.fefu.ecommerceapi.services.ActivationCodeService;
import ru.fefu.ecommerceapi.services.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ActivationCodeService activationCodeService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto user) {
        return ResponseEntity.ok(authService.login(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok(authService.refresh(refreshRequest.getRefreshToken()));
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activate(@RequestBody ActivationRequest activationRequest) {
        activationCodeService.activate(activationRequest);
        return ResponseEntity.ok().build();
    }

}
