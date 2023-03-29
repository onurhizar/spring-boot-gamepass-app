package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.Game;
import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.model.enums.UserRole;
import com.onurhizar.gamepass.model.request.CreateUserRequest;
import com.onurhizar.gamepass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final GameService gameService;
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;


    public User addUser(CreateUserRequest request){
        User foundUser = repository.findByEmail(request.getEmail()); // check if email exists
        if (foundUser != null) throw new RuntimeException("email exists"); // TODO make specific exception

        User newUser = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.GUEST) // default
                .build();
        return repository.save(newUser);
    }

    public void deleteUser(String userId){
        User user = findById(userId);
        repository.delete(user);
    }


    public User updateUser(String id, CreateUserRequest userDto) {
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        // TODO : a better approach? too much repetition
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getSurname() != null) user.setSurname(userDto.getSurname());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        }
        return repository.save(user);
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

    public User followCategory(String userId, String categoryId){
        return followHelper(userId, categoryId, true);
    }

    public User unfollowCategory(String userId, String categoryId){
        return followHelper(userId, categoryId, false);
    }


    /**
     * Helper method to avoid duplicate codes.
     * boolean isAddition field checks if it is a favorite or unfavorite method
     */
    private User addOrRemoveGameFromUserFavoriteGames(String userId, String gameId, boolean isAddition){
        User user = findById(userId);
        Game game = gameService.findGameById(gameId);
        List<Game> games = user.getFavoriteGames();

        if (isAddition && !games.contains(game)) games.add(game);
        else games.remove(game);
        repository.save(user);
        return user;
    }

    /**
     * Helper method to avoid duplicate codes. <br>
     * (TODO still needs a refactor of duplicate codes with addOrRemoveGameFromUserFavoriteGames method) <br>
     * To refactor, maybe use ICrudService interface? and give the method of class and its service. <br>
     * boolean isFollow field checks if it is a follow or unfollow request
     */
    private User followHelper(String userId, String categoryId, boolean isFollow){
        User user = findById(userId);
        Category category = categoryService.findCategoryById(categoryId);

        List<Category> categories = user.getFollowedCategories();

        if (isFollow && !categories.contains(category)) categories.add(category);
        else categories.remove(category);
        repository.save(user);
        return user;
    }
}
