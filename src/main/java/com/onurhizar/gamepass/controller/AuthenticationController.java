package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.request.auth.LoginRequest;
import com.onurhizar.gamepass.model.request.auth.RegisterRequest;
import com.onurhizar.gamepass.model.response.AuthenticationResponse;
import com.onurhizar.gamepass.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@Valid @RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }

}