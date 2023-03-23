package com.onurhizar.gamepass.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("games")
    private Set<Category> categories = new HashSet<>();

}
