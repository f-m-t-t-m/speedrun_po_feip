package ru.fefu.ecommerceapi.services;

import ch.qos.logback.core.joran.spi.ActionException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.process.internal.UserTypeResolution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.ActivationRequest;
import ru.fefu.ecommerceapi.entity.ActivationCode;
import ru.fefu.ecommerceapi.entity.User;
import ru.fefu.ecommerceapi.exceptions.ActivationException;
import ru.fefu.ecommerceapi.repository.ActivationCodeRepository;
import ru.fefu.ecommerceapi.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ActivationCodeService {

    private final ActivationCodeRepository activationCodeRepository;
    private final UserRepository userRepository;
    @Value("${sms.expiration-time}")
    private Duration smsExpiration;

    @Transactional
    public void activate(ActivationRequest activationRequest) {
        User user = userRepository.findByPhone(activationRequest.getPhone())
                .orElseThrow(ActivationException::new);

        ActivationCode activationCode = activationCodeRepository.findActivationCodeByCodeAndUserPhone(
                activationRequest.getCode(),
                activationRequest.getPhone()
        ).orElseThrow(ActivationException::new);
        verify(activationCode);

        activationCodeRepository.delete(activationCode);
        user.setEnabled(true);
    }

    public void save(String phone, String code) {
        User user = userRepository.findByPhone(phone).orElseThrow(ActivationException::new);

        ActivationCode activationCode = ActivationCode.builder()
                .user(user)
                .code(code)
                .expirationDate(LocalDateTime.now().plus(smsExpiration))
                .build();

        activationCodeRepository.save(activationCode);
    }

    private void verify(ActivationCode activationCode) {
        if (activationCode.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ActivationException();
        }
    }

}
