package com.onurhizar.gamepass.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
