package com.onurhizar.gamepass.model.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Payment extends CommonEntity {
    @Id
    private String id;
    private int amount;
    private String senderCard;
    private String receiverCard;

    @ManyToOne
    private Invoice invoice;
}