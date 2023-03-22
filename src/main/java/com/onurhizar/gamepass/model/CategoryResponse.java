package com.onurhizar.gamepass.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.onurhizar.gamepass.entity.Category;
import com.onurhizar.gamepass.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class CategoryResponse {

    private String name;
    private boolean isSuperCategory;

    @JsonIgnoreProperties(value = {"id","categories"})
    private Set<Game> games;

    public static CategoryResponse fromEntity(Category category){
        return new CategoryResponse(
                category.getName(),
                category.isSuperCategory(),
                category.getGames()
        );
    }
}
