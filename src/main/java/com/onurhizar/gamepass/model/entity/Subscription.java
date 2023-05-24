package com.onurhizar.gamepass.model.entity;

import javax.persistence.Entity;

import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription extends BaseEntity {

    private String name;
    private int monthlyFee;
    private int duration;
    private boolean isActive;
}
