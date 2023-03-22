package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.entity.Game;
import com.onurhizar.gamepass.model.GameResponse;
import com.onurhizar.gamepass.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GameService {

    private GameRepository repository;

    public void addGame(Game game){
        repository.save(game);
    }

    public List<GameResponse> listGames(){
        return repository.findAll().stream()
                .map(GameResponse::fromEntity).toList();
    }
}
