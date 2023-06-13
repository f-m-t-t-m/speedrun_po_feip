package ru.fefu.ecommerceapi.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.enums.ReceiveType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {

    @NotBlank
    private String name;
    private String email;
    @NotNull
    private ReceiveType receiveType;
    private AddressDto address;
    private String comment;
    @NotEmpty
    private String cartUuid;

}
