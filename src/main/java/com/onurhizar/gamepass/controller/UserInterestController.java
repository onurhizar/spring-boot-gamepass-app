package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.model.response.UserResponse;
import com.onurhizar.gamepass.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/{userId}")
@RequiredArgsConstructor
public class UserInterestController {

    private final UserService userService;

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
