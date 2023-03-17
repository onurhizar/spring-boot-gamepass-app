package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.entity.Game;
import com.onurhizar.gamepass.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public List<Game> listGames(){
        return gameService.listGames();
    }
}
