package com.onurhizar.gamepass.entity;

import com.onurhizar.gamepass.model.enums.GameCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @Enumerated(EnumType.STRING)
    private GameCategory category;
}
