package ru.fefu.ecommerceapi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    @Pattern(regexp = "^\\d{11}$")
    private String phone;
    @Email
    private String email;
    @NotBlank
    private String password;

}
