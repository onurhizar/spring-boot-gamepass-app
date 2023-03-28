package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.entity.User;
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
    public User favoriteGame(@PathVariable String userId, @PathVariable String gameId){
        return userService.favoriteGame(userId, gameId);
    }

    @PostMapping("/game/{gameId}/unfavorite")
    public User unfavoriteGame(@PathVariable String userId, @PathVariable String gameId){
        return userService.unfavoriteGame(userId, gameId);
    }
}
