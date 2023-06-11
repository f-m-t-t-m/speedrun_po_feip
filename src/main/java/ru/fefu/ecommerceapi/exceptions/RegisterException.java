package ru.fefu.ecommerceapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterException extends ApiException {

    private Throwable cause;
    private String message;

    public RegisterException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public RegisterException(String message) {
        super(message);
        this.message = message;
    }

}
