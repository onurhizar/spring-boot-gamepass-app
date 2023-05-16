package com.onurhizar.gamepass.model.request.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, max = 32)
    private String password;

}
