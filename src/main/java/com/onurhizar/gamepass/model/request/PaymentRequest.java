package com.onurhizar.gamepass.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequest {
    @NotNull
    private int amount;
    @NotBlank
    private String senderCard;
    @NotBlank
    private String receiverCard;
}
