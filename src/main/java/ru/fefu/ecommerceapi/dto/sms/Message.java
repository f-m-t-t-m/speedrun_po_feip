package ru.fefu.ecommerceapi.dto.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String phone;
    @Builder.Default
    private String sender = "MediaGramma";
    private String text;

}
