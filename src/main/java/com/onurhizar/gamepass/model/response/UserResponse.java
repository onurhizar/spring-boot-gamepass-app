package com.onurhizar.gamepass.model.response;

import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.Game;
import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.model.enums.UserRole;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private String id;
    private String name;
    private String surname;
    private String email;
    private UserRole role;
    private boolean verified;

    private List<String> favoriteGames;
    private List<String> followedCategories;

    private ZonedDateTime createdAt; // TODO: extend with abstract class? But @Builder makes it harder to implement
    private ZonedDateTime updatedAt;

    public static UserResponse fromEntity(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .verified(user.isVerified())
                .favoriteGames(user.getFavoriteGames().stream().map(Game::getTitle).toList())
                .followedCategories(user.getFollowedCategories().stream().map(Category::getName).toList())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
