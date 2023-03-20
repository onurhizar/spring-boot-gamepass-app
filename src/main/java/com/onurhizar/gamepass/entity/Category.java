package com.onurhizar.gamepass.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "categories_games",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Game> games = new HashSet<>();
}
