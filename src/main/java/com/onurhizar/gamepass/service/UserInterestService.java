package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.Game;
import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.model.response.CategoryResponse;
import com.onurhizar.gamepass.model.response.GameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInterestService {

    private final UserService userService;

    public List<GameResponse> getFavoriteGamesOfUser(String userId) {
        User user = userService.findById(userId);
        return user.getFavoriteGames().stream()
                .map(GameResponse::fromEntity).collect(Collectors.toList());
    }

    public Game getFavoriteGameDetailOfUser(String userId, String gameId) {
        User user = userService.findById(userId);
        for (Game game : user.getFavoriteGames()) {
            if (game.getId().equals(gameId)) return game;
        }
        throw new EntityNotFoundException();
    }

    public List<CategoryResponse> getFollowedCategoriesOfUser(String userId) {
        User user = userService.findById(userId);
        return user.getFollowedCategories().stream()
                .map(CategoryResponse::fromEntity).collect(Collectors.toList());
    }

    public Category getFollowedCategoryDetailOfUser(String userId, String categoryId) {
        User user = userService.findById(userId);
        for (Category category : user.getFollowedCategories()) {
            if (category.getId().equals(categoryId)) return category;
        }
        throw new EntityNotFoundException();
    }

}
