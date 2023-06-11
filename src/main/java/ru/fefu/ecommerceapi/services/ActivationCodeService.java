package ru.fefu.ecommerceapi.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.auth.ActivationRequest;
import ru.fefu.ecommerceapi.entity.ActivationCode;
import ru.fefu.ecommerceapi.entity.User;
import ru.fefu.ecommerceapi.exceptions.ActivationException;
import ru.fefu.ecommerceapi.exceptions.NotFoundException;
import ru.fefu.ecommerceapi.repository.ActivationCodeRepository;
import ru.fefu.ecommerceapi.repository.UserRepository;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ActivationCodeService {

    private final ActivationCodeRepository activationCodeRepository;
    private final UserRepository userRepository;
    private final SmsService smsService;
    @Value("${sms.expiration-time}")
    private Duration smsExpiration;

    @Transactional
    public void activate(ActivationRequest activationRequest) {
        User user = userRepository.findByPhone(activationRequest.getPhone())
                .orElseThrow(NotFoundException::new);

        ActivationCode activationCode = activationCodeRepository.findActivationCodeByCodeAndUserPhone(
                activationRequest.getCode(),
                activationRequest.getPhone()
        ).orElseThrow(() -> new ActivationException("Неправильно введен код активации."));
        verify(activationCode);

        activationCodeRepository.delete(activationCode);
        user.setEnabled(true);
    }

    public void sendActivationToken(String phone) {
        String code = generateToken();
        save(phone, code);
        smsService.sendSms(phone, code);
    }

    public void resendActivationToken(String phone) {
        ActivationCode activationCode = activationCodeRepository.findActivationCodeByUserPhone(phone)
                .orElseThrow(ActivationException::new);
        resendVerify(activationCode);
        activationCodeRepository.delete(activationCode);

        String code = generateToken();
        save(phone, code);
        smsService.sendSms(phone, code);
    }

    private void save(String phone, String code) {
        User user = userRepository.findByPhone(phone).orElseThrow(NotFoundException::new);

        ActivationCode activationCode = ActivationCode.builder()
                .user(user)
                .code(code)
                .issueDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plus(smsExpiration))
                .build();

        activationCodeRepository.save(activationCode);
    }

    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        return String.valueOf(secureRandom.nextInt(1000, 10000));
    }

    private void resendVerify(ActivationCode activationCode) {
        if (ChronoUnit.SECONDS.between(activationCode.getIssueDate(), LocalDateTime.now()) < 60) {
            throw new ActivationException("Прошло меньше 60 секунд перед переотправкой кода активации.");
        }
    }

    private void verify(ActivationCode activationCode) {
        if (activationCode.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ActivationException("Код активации истёк.");
        }
    }

}
