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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Page<GameResponse> listGames(Pageable pageable){
        return repository.findAll(pageable).map(GameResponse::fromEntity);
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

    public Game addCategoryToGame(String gameId, String categoryId) {
        Game game = findGameById(gameId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);

        // check if already exists
        if (game.getCategories().contains(category)) return game;

        game.getCategories().add(category);
        category.getGames().add(game);
        saveGame(game);
        categoryRepository.save(category);
        return game;
    }
}
