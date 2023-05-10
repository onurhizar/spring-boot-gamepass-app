package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.request.CreateGameRequest;
import com.onurhizar.gamepass.model.response.GameResponse;
import com.onurhizar.gamepass.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public GameResponse postGame(@RequestBody CreateGameRequest createGameRequest){
        return GameResponse.fromEntity(gameService.createGame(createGameRequest));
    }

    @PutMapping("{gameId}")
    public GameResponse updateGame(@PathVariable String gameId, @RequestBody CreateGameRequest createGameRequest){
        return GameResponse.fromEntity(gameService.updateGame(gameId,createGameRequest));
    }

    @DeleteMapping("{gameId}")
    public void deleteGame(@PathVariable String gameId){
        gameService.deleteGame(gameId);
    }
}
