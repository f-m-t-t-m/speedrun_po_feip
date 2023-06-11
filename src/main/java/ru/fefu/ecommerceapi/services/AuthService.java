package ru.fefu.ecommerceapi.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.auth.TokenResponse;
import ru.fefu.ecommerceapi.dto.auth.UserDto;
import ru.fefu.ecommerceapi.entity.User;
import ru.fefu.ecommerceapi.mappers.UserMapper;
import ru.fefu.ecommerceapi.repository.UserRepository;
import ru.fefu.ecommerceapi.security.JwtService;
import ru.fefu.ecommerceapi.security.RefreshTokenService;
import ru.fefu.ecommerceapi.security.Role;

import java.security.SecureRandom;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final SmsService smsService;
    private final RefreshTokenService refreshTokenService;
    private final ActivationCodeService activationCodeService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto register(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.dtoToEntity(userDto);
        user.setRole(Role.USER);
        userRepository.save(user);
        SecureRandom secureRandom = new SecureRandom();
        String code = String.valueOf(secureRandom.nextInt(1000, 10000));
        activationCodeService.save(userDto.getPhone(), code);
        smsService.sendSms(user.getPhone(), code);
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
                .orElseThrow(() -> new UsernameNotFoundException("123"));
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

}
