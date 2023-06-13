package ru.fefu.ecommerceapi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum Role {

    ADMIN,
    USER;

    public String toString() {
        return this.name();
    }

}
