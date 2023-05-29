package com.onurhizar.gamepass.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateSubscriptionRequest {
    private String name;
    private int monthlyFee;
    private int duration;
    private boolean active;
}
