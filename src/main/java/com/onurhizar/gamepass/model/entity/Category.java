package com.onurhizar.gamepass.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {

    private String name;

    @OneToOne
    private Category parent;

    private boolean isSuperCategory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "categories_games",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    @JsonIgnoreProperties("categories")
    @Builder.Default
    private List<Game> games = new LinkedList<>();


    @Override
    public String toString() {
        return "Category{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", isSuperCategory=" + isSuperCategory +
                '}';
    }
}
