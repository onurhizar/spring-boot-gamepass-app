package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.response.GameResponse;
import com.onurhizar.gamepass.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public List<GameResponse> listGames(){
        return gameService.listGames();
    }

    @GetMapping("{gameId}")
    public GameResponse getGame(@PathVariable String gameId){
        return GameResponse.fromEntity(gameService.getGame(gameId));
    }
}
