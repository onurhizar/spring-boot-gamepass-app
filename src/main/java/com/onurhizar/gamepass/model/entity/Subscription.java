package com.onurhizar.gamepass.model.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subscription extends BaseEntity {

    private String name;
    private int monthlyFee;
    private int duration;
    private boolean isActive;
}
