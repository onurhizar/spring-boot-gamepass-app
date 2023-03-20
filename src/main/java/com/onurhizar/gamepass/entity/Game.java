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
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("games") // TODO : remove later when using DTO
    @EqualsAndHashCode.Exclude
    private Set<Category> categories = new HashSet<>();

}
