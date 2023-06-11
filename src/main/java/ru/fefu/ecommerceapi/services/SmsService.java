package ru.fefu.ecommerceapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${sms.url}")
    private String url;
    @Value("${sms.login}")
    private String username;
    @Value("${sms.password}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public void sendSms(String phone, String code) {
        ResponseEntity<String> response = restTemplate.exchange(constructUrl(phone, code), HttpMethod.GET,
                new HttpEntity<String>(createHeaders(username, apiKey)), String.class);
        log.info("sms response: {}", response.getBody());
    }

    private String constructUrl(String phone, String code) {
        return url + String.format("number=%s&text=%s&sign=SMS Aero", phone, code);
    }

    private HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            String encodedAuth = Base64.encodeBase64String(auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + encodedAuth;
            set("Authorization", authHeader);
        }};
    }

}
