package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.Game;
import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.model.request.CreateGameRequest;
import com.onurhizar.gamepass.model.response.GameResponse;
import com.onurhizar.gamepass.repository.CategoryRepository;
import com.onurhizar.gamepass.repository.GameRepository;
import com.onurhizar.gamepass.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GameService {

    private GameRepository repository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public void saveGame(Game game){
        repository.save(game);
    }

    public Game findGameById(String gameId){
        return repository.findById(gameId).orElseThrow(EntityNotFoundException::new);
    }

    public List<GameResponse> listGames(){
        return repository.findAll().stream()
                .map(GameResponse::fromEntity).toList();
    }

    public Game getGame(String gameId) {
        return findGameById(gameId);
    }

    public Game createGame(CreateGameRequest createGameRequest) {
        Game game = Game.builder()
                .title(createGameRequest.getTitle())
                .build();
        saveGame(game);
        return game;
    }

    public Game updateGame(String gameId, CreateGameRequest createGameRequest) {
        Game game = findGameById(gameId);
        game.setTitle(createGameRequest.getTitle());
        saveGame(game);
        return game;
    }

    public void deleteGame(String gameId) {
        Game game = findGameById(gameId); // throw error if not found

        // remove associations
        List<Category> categories = categoryRepository.findCategoriesByGames(game);
        for (Category category : categories) {
            category.getGames().remove(game);
            categoryRepository.save(category);
        }

        List<User> users = userRepository.findByFavoriteGames(game);
        for (User user : users) {
            user.getFavoriteGames().remove(game);
            userRepository.save(user);
        }

        repository.deleteById(gameId);
    }
}
