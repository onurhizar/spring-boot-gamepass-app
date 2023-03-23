package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> listGames(){
        return userService.listUsers();
    }
}
