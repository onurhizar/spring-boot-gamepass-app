package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.model.request.CreateUserRequest;
import com.onurhizar.gamepass.model.response.UserResponse;
import com.onurhizar.gamepass.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> listUsers(){
        return userService.listUsers().stream().map(UserResponse::fromEntity).toList();
    }

    @PostMapping
    public UserResponse addUser(@Valid @RequestBody CreateUserRequest request){
        return UserResponse.fromEntity(userService.addUser(request));
    }

    @PutMapping("{userId}")
    public UserResponse updateUser(@Valid @RequestBody CreateUserRequest request, @PathVariable String userId){
        return UserResponse.fromEntity(userService.updateUser(userId, request));
    }

    @DeleteMapping("{userId}")
   public void deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
   }
}
