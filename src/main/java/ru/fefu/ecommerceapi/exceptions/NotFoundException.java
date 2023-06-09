package ru.fefu.ecommerceapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundException extends ApiException {

    private String message;
    private Throwable cause;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
