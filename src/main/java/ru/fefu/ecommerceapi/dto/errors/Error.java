package ru.fefu.ecommerceapi.dto.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    private List<String> messages;
    private Integer code;
    private String exception;

}
