package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.request.auth.LoginRequest;
import com.onurhizar.gamepass.model.request.auth.RegisterRequest;
import com.onurhizar.gamepass.model.response.AuthenticationResponse;
import com.onurhizar.gamepass.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public String sendVerificationCodeToEmail(@RequestParam String email, HttpServletRequest request){
        String domain = request.getServerName();
        int port = request.getServerPort();
        String code = authenticationService.sendVerificationCodeToEmail(email);
        if (port == 80) return "Your verification link is http://" + domain + "/verify?code=" + code;
        else if (port == 443) return "Your verification link is https://" + domain + "/verify?code=" + code;
        return "Your verification link is http://" + domain + ":" + port + "/verify?code=" + code;
    }


    @GetMapping("/recover")
    // sends a new random generated password
    public String recoverPassword(@RequestParam String code){
        String newPassword = authenticationService.recoverPasswordByGeneratingNew(code);
        return "Your new password is "+newPassword;
    }

    @GetMapping("/recover/email/{userId}")
    // TODO email service is later, for now it just sends HTTP response
    public String sendRecoveryCodeToEmail(@PathVariable String userId, HttpServletRequest request){
        String domain = request.getServerName();
        int port = request.getServerPort();
        String code = authenticationService.createRecoveryCode(userId);

        if (port == 80) return "Your verification link is http://" + domain + "/recover?code=" + code;
        else if (port == 443) return "Your verification link is https://" + domain + "/recover?code=" + code;
        return "Your verification link is http://" + domain + ":" + port + "/recover?code=" + code;
    }

}
