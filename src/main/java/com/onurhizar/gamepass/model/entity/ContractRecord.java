package com.onurhizar.gamepass.model.entity;

import javax.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractRecord extends BaseEntity {

    private String name;
    private int monthlyFee;
    private int duration;

    @Column(name = "is_active")
    private boolean active;

    @OneToOne
    private User user;
    private ZonedDateTime createdDate;

    @Override
    public String toString() {
        return "ContractRecord [name=" + name + ", monthlyFee=" + monthlyFee + ", duration=" + duration + ", isActive="
                + active + ", userId=" + user.getId() + ", createdDate=" + createdDate + "]";
    }
}
