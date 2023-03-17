package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.entity.Game;
import com.onurhizar.gamepass.repository.GameRepository;
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

    public List<Game> listGames(){
        return repository.findAll();
    }
}
