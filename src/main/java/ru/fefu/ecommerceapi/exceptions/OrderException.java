package ru.fefu.ecommerceapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.fefu.ecommerceapi.dto.cart.CartDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderException extends ApiException {

    private Throwable cause;
    private String message;
    private CartDto cartDto;

    public OrderException(CartDto cartDto) {
        super();
        this.cartDto = cartDto;
    }

    public OrderException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public OrderException(String message) {
        super(message);
        this.message = message;
    }

}
