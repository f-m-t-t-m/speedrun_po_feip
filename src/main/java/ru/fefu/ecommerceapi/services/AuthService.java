package ru.fefu.ecommerceapi.services;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.fefu.ecommerceapi.dto.auth.TokenResponse;
import ru.fefu.ecommerceapi.dto.auth.UserDto;
import ru.fefu.ecommerceapi.entity.User;
import ru.fefu.ecommerceapi.exceptions.RegisterException;
import ru.fefu.ecommerceapi.mappers.UserMapper;
import ru.fefu.ecommerceapi.repository.UserRepository;
import ru.fefu.ecommerceapi.security.JwtService;
import ru.fefu.ecommerceapi.security.RefreshTokenService;
import ru.fefu.ecommerceapi.security.Role;

import java.util.Map;

@Service
@Validated
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final ActivationCodeService activationCodeService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto register(@Valid UserDto userDto) {
        User user = userRepository.findByPhone(userDto.getPhone()).orElseGet(User::new);
        if (user.getEnabled()) {
            throw new RegisterException("Данный телефон уже используется.");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userMapper.updateUser(userDto, user);
        user.setRole(Role.USER);
        userRepository.save(user);

        activationCodeService.sendActivationToken(userDto.getPhone());
        return userMapper.entityToDto(user);
    }

    public TokenResponse login(UserDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getPhone(),
                        userDto.getPassword()
                )
        );
        User user = userRepository.findByPhone(userDto.getPhone())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Неверно указан логин или пароль"));
        String accessToken = jwtService.createJwt(Map.of("Role", user.getRole().toString()), user);
        String refreshToken = refreshTokenService.generate(user);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenResponse refresh(String refreshToken) {
        return refreshTokenService.refresh(refreshToken);
    }

    public User getUserFromAuth(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return (User) authentication.getPrincipal();
    }

    @PostConstruct
    private void createTestUsers() {
        if (userRepository.findByPhone("user").isEmpty()) {
            User user = User.builder()
                    .firstName("user")
                    .lastName("user")
                    .password(passwordEncoder.encode("user"))
                    .phone("user")
                    .enabled(true)
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
        }

        if (userRepository.findByPhone("admin").isEmpty()) {
            User admin = User.builder()
                    .firstName("admin")
                    .lastName("admin")
                    .password(passwordEncoder.encode("admin"))
                    .phone("admin")
                    .enabled(true)
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
        }
    }

}
