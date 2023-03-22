package com.onurhizar.gamepass.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onurhizar.gamepass.entity.Category;
import com.onurhizar.gamepass.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class GameResponse {

    private String title;

    @JsonIgnoreProperties(value = {"id","games","parent"})
    private Set<Category> categories;

    public static GameResponse fromEntity(Game game){
        return new GameResponse(
                game.getTitle(),
                game.getCategories()
        );
    }
}
