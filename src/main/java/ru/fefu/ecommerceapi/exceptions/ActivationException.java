package ru.fefu.ecommerceapi.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActivationException extends ApiException {

    private Throwable cause;

    public ActivationException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

}
