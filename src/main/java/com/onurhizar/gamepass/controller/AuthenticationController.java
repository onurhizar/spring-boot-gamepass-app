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


    @GetMapping("/verify") // TODO: in the API contract it is said POST method but I think GET is more intuitive
    public void verify(@RequestParam String code){
        authenticationService.verify(code);
    }

    @GetMapping("/verify/email")
    // TODO email service is later, for now it just sends HTTP response
    public String sendVerificationCodeToEmail(@RequestParam String email){
        String code = authenticationService.sendVerificationCodeToEmail(email);
        return "Your verification link is http://localhost:8080/verify?code="+code;
    }

}
