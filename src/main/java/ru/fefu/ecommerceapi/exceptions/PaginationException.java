package ru.fefu.ecommerceapi.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaginationException extends ApiException{

    private String message;

}
