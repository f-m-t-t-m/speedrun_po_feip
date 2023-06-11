package ru.fefu.ecommerceapi.exceptions.handlers;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.fefu.ecommerceapi.dto.errors.Error;
import ru.fefu.ecommerceapi.exceptions.*;

import java.util.List;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e, WebRequest webRequest) {
        log.error("error occured ", e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<?> handleRefreshTokenException(RefreshTokenException e, WebRequest webRequest) {
        log.error("error occured ", e);
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ActivationException.class)
    public ResponseEntity<?> handleActivationException(ActivationException e, WebRequest webRequest) {
        log.error("error occured ", e);
        return new ResponseEntity<>(new Error(List.of(e.getMessage()), 409, e.getClass().getName()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<?> handleRegisterException(RegisterException e, WebRequest webRequest) {
        log.error("error occured ", e);
        return new ResponseEntity<>(new Error(List.of(e.getMessage()), 409, e.getClass().getName()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e, WebRequest webRequest) {
        log.error("error occured ", e);
        return new ResponseEntity<>(new Error(List.of(e.getMessage()), 401, e.getClass().getName()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<?> handleOrderException(OrderException e, WebRequest webRequest) {
        log.error("error occured ", e);
        return new ResponseEntity<>(new Error(List.of(e.getMessage()), 409, e.getClass().getName()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e, WebRequest webRequest) {
        log.error("error occured ", e);
        List<String> violationList = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + " " + violation.getMessage()
                ).toList();
        return new ResponseEntity<>(new Error(violationList, 400, e.getClass().getName()), HttpStatus.BAD_REQUEST);
    }

}
