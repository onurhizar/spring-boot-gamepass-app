package com.onurhizar.gamepass.service;


import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.Game;
import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;
    private GameService gameService;

    public void addUser(User user){
        repository.save(user);
    }

    public List<User> listUsers(){
        return repository.findAll();
    }

    public User findById(String id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    // Interests : Follow Categories and Favorite Games
    public User favoriteGame(String userId, String gameId){
        return addOrRemoveGameFromUserFavoriteGames(userId, gameId, true);
    }

    public User unfavoriteGame(String userId, String gameId){
        return addOrRemoveGameFromUserFavoriteGames(userId, gameId, false);
    }

    /**
     * Helper method to avoid duplicate codes.
     * boolean isAddition field checks if it is a favorite or unfavorite method
     */
    private User addOrRemoveGameFromUserFavoriteGames(String userId, String gameId, boolean isAddition){
        User user = findById(userId);
        Game game = gameService.findGame(gameId);
        List<Game> games = user.getFavoriteGames();

        if (isAddition && !games.contains(game)) games.add(game);
        else games.remove(game);
        repository.save(user);
        return user;
    }
}
