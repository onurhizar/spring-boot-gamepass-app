package com.onurhizar.gamepass.model.entity;

import javax.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractRecord extends BaseEntity {

    private String name;
    private int monthlyFee;
    private int duration;
    private boolean isActive;

    @OneToOne
    private User user;
    private ZonedDateTime createdDate;
}
