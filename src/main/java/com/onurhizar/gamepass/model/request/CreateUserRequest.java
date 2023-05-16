package com.onurhizar.gamepass.model.request;

import com.onurhizar.gamepass.model.entity.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This DTO is also used for updating user.
 * If it will be necessary, separate DTO will be implemented.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;

    public static User toEntity(CreateUserRequest request){
        return User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .passwordHash(request.getPassword())
                .build();
    }
}
