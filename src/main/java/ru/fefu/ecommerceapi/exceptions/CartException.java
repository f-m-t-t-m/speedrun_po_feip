package ru.fefu.ecommerceapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartException extends ApiException {

    private Throwable cause;
    private String message;

    public CartException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public CartException(String message) {
        super(message);
        this.message = message;
    }

}
