package com.onurhizar.gamepass.model.response;

import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class GameResponse {

    private String title;
    private List<String> categories;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static GameResponse fromEntity(Game game){
        return new GameResponse(
                game.getTitle(),
                game.getCategories().stream().map(Category::getName).toList(),
                game.getCreatedAt(),
                game.getUpdatedAt()
        );
    }
}
