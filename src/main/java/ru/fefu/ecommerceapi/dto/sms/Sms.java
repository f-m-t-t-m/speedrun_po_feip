package ru.fefu.ecommerceapi.dto.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sms {

    private List<Message> messages = new ArrayList<>();
    private String login;
    private String password;

}
