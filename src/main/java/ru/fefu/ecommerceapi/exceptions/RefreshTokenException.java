package ru.fefu.ecommerceapi.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenException extends ApiException {

    private Throwable cause;

    public RefreshTokenException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

}
