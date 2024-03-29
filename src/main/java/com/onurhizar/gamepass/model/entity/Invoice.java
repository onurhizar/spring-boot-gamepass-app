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

    @Override
    public String toString() {
        return "Invoice{" + "id=" + getId() +
                ", fee=" + fee +
                ", contractRecordID=" + contractRecord.getId() +
                '}';
    }
}
