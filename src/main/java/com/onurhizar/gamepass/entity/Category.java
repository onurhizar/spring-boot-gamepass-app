package com.onurhizar.gamepass.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @OneToOne
    private Category parent;

    private boolean isSuperCategory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "categories_games",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    @JsonIgnoreProperties("categories") // TODO : remove later when using DTO
    @EqualsAndHashCode.Exclude
    private Set<Game> games = new HashSet<>();
}
