package ru.fefu.ecommerceapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.TokenResponse;
import ru.fefu.ecommerceapi.entity.RefreshToken;
import ru.fefu.ecommerceapi.entity.User;
import ru.fefu.ecommerceapi.exceptions.RefreshTokenException;
import ru.fefu.ecommerceapi.repository.RefreshTokenRepository;
import ru.fefu.ecommerceapi.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${jwt.refresh-expiration-time}")
    private Duration refreshExpirationTime;

    public String generate(User user) {
        refreshTokenRepository.findByUserId(user.getId())
                .ifPresent(refreshTokenRepository::delete);

        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expirationDate(LocalDateTime.now().plus(refreshExpirationTime))
                .build();

        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public TokenResponse refresh(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(RefreshTokenException::new);
        verify(refreshToken);
        String jwt = jwtService.createJwt(Map.of("Role", refreshToken.getUser().getRole().toString()), refreshToken.getUser());
        String refresh = generate(refreshToken.getUser());
        refreshTokenRepository.delete(refreshToken);
        return TokenResponse.builder()
                .accessToken(jwt)
                .refreshToken(refresh)
                .build();
    }

    private void verify(RefreshToken refreshToken) {
        if (refreshToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RefreshTokenException();
        }
    }

}
