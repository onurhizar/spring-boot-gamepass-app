package com.onurhizar.gamepass.model.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice extends BaseEntity {

    private int fee;

    @ManyToOne
    private ContractRecord contractRecord;
}
