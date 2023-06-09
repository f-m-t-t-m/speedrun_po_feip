package ru.fefu.ecommerceapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivationException extends ApiException {

    private Throwable cause;
    private String message;

    public ActivationException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public ActivationException(String message) {
        super(message);
        this.message = message;
    }

}
