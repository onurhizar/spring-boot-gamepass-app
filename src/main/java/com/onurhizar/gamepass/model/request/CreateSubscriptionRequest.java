package com.onurhizar.gamepass.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CreateSubscriptionRequest {
    private String name;
    private int monthlyFee;
    private int duration;
    private boolean active;
}
