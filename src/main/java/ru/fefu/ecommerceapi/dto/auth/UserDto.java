package ru.fefu.ecommerceapi.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;

}
