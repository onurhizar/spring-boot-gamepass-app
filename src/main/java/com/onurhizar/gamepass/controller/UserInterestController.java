package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.model.response.CategoryResponse;
import com.onurhizar.gamepass.model.response.GameResponse;
import com.onurhizar.gamepass.model.response.UserResponse;
import com.onurhizar.gamepass.service.UserInterestService;
import com.onurhizar.gamepass.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}")
@RequiredArgsConstructor
public class UserInterestController {

    private final UserService userService;
    private final UserInterestService userInterestService;

    @GetMapping("/game")
    public List<GameResponse> getFavoriteGamesOfUser(@PathVariable String userId){
        return userInterestService.getFavoriteGamesOfUser(userId);
    }

    @GetMapping("/game/{gameId}")
    public GameResponse getFavoriteGameDetailOfUser(@PathVariable String userId, @PathVariable String gameId){
        return GameResponse.fromEntity(userInterestService.getFavoriteGameDetailOfUser(userId, gameId));
    }

    @GetMapping("/category")
    public List<CategoryResponse> getFollowedCategoriesOfUser(@PathVariable String userId){
        return userInterestService.getFollowedCategoriesOfUser(userId);
    }

    @GetMapping("/category/{categoryId}")
    public CategoryResponse getFollowedCategoryDetailOfUser(@PathVariable String userId, @PathVariable String categoryId) {
        return CategoryResponse.fromEntity(
                userInterestService.getFollowedCategoryDetailOfUser(userId, categoryId)
        );
    }


    @PostMapping("/game/{gameId}/favorite")
    public UserResponse favoriteGame(@PathVariable String userId, @PathVariable String gameId){
        return UserResponse.fromEntity(userService.favoriteGame(userId, gameId));
    }

    @PostMapping("/game/{gameId}/unfavorite")
    public UserResponse unfavoriteGame(@PathVariable String userId, @PathVariable String gameId){
        return UserResponse.fromEntity(userService.unfavoriteGame(userId, gameId));
    }

    @PostMapping("/category/{categoryId}/follow")
    public UserResponse followCategory(@PathVariable String userId, @PathVariable String categoryId){
        return UserResponse.fromEntity(userService.followCategory(userId, categoryId));
    }

    @PostMapping("/category/{categoryId}/unfollow")
    public UserResponse unfollowCategory(@PathVariable String userId, @PathVariable String categoryId){
        return UserResponse.fromEntity(userService.unfollowCategory(userId, categoryId));
    }
}
