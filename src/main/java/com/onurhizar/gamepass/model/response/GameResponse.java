package com.onurhizar.gamepass.model.response;

import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GameResponse {

    private String title;
    private List<String> categories;

    public static GameResponse fromEntity(Game game){
        return new GameResponse(
                game.getTitle(),
                game.getCategories().stream().map(Category::getName).toList()
        );
    }
}
