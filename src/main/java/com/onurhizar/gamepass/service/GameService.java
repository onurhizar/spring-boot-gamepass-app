package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.model.entity.Game;
import com.onurhizar.gamepass.model.response.GameResponse;
import com.onurhizar.gamepass.repository.GameRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GameService {

    private GameRepository repository;

    public void addGame(Game game){
        repository.save(game);
    }

    @Transactional
    public List<GameResponse> listGames(){
        return repository.findAll().stream()
                .map(GameResponse::fromEntity).toList();
    }
}
