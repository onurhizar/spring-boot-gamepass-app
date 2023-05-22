package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.request.CreateGameRequest;
import com.onurhizar.gamepass.model.response.GameResponse;
import com.onurhizar.gamepass.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public Page<GameResponse> listGames(@ParameterObject Pageable pageable) {
        return gameService.listGames(pageable);
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

    @PostMapping("{gameId}/category/{categoryId}")
    public GameResponse addCategoryToGame(@PathVariable String gameId, @PathVariable String categoryId){
        return GameResponse.fromEntity(gameService.addCategoryToGame(gameId, categoryId));
    }
}
