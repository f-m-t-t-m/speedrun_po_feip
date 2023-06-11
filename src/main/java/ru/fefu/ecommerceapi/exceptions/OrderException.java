package ru.fefu.ecommerceapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderException extends ApiException {

    private Throwable cause;
    private String message;

    public OrderException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public OrderException(String message) {
        super(message);
        this.message = message;
    }

}
