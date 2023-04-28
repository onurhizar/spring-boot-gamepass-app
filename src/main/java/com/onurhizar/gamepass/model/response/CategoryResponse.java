package com.onurhizar.gamepass.model.response;

import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class CategoryResponse {

    private String name;
    private boolean isSuperCategory;
    private List<String> games;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static CategoryResponse fromEntity(Category category){
        return new CategoryResponse(
                category.getName(),
                category.isSuperCategory(),
                category.getGames().stream().map(Game::getTitle).toList(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
