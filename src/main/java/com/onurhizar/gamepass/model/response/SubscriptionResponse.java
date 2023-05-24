package com.onurhizar.gamepass.model.response;

import com.onurhizar.gamepass.model.entity.Invoice;
import com.onurhizar.gamepass.model.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionResponse {
    private String id;

    private String name;
    private int monthlyFee;
    private int duration;
    private boolean active;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static SubscriptionResponse fromEntity(Subscription subscription){
        return SubscriptionResponse.builder()
                .id(subscription.getId())
                .name(subscription.getName())
                .monthlyFee(subscription.getMonthlyFee())
                .duration(subscription.getDuration())
                .active(subscription.isActive())
                .createdAt(subscription.getCreatedAt())
                .updatedAt(subscription.getUpdatedAt())
                .build();
    }
}
