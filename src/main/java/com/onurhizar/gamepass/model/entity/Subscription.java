package com.onurhizar.gamepass.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subscription extends CommonEntity {
    @Id
    private String id;
    private String name;
    private int monthlyFee;
    private int duration;
    private boolean isActive;
}
