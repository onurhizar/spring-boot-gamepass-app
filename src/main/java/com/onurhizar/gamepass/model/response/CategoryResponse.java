package com.onurhizar.gamepass.model.response;

import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class CategoryResponse {

    private String id;
    private String name;
    private boolean isSuperCategory;
    private List<String> games;
    private List<String> parentCategories;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static CategoryResponse fromEntity(Category category){
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.isSuperCategory(),
                category.getGames().stream().map(Game::getTitle).collect(Collectors.toList()),
                getParentCategories(category),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    private static List<String> getParentCategories(Category category) {
        List<String> parents = new LinkedList<>();

        Category parentNode = category.getParent();
        while (parentNode != null){
            parents.add(parentNode.getName());
            parentNode = parentNode.getParent();
        }
        return parents;
    }
}
