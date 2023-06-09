package ru.fefu.ecommerceapi.exceptions;

import lombok.Getter;

@Getter
public class FileUploadException extends ApiException {

    private final Throwable cause;

    public FileUploadException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

}
