package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.model.enums.UserRole;
import com.onurhizar.gamepass.model.request.auth.RegisterRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.onurhizar.gamepass.model.request.auth.LoginRequest;
import com.onurhizar.gamepass.model.response.AuthenticationResponse;
import com.onurhizar.gamepass.repository.UserRepository;
import com.onurhizar.gamepass.security.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository; // TODO make this userService
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
            .name(request.getName())
            .surname(request.getSurname())
            .email(request.getEmail())
            .passwordHash(passwordEncoder.encode(request.getPassword()))
            .role(UserRole.GUEST)
            .build();

        // check if user exists before register
        User foundUser = userRepository.findByEmail(request.getEmail());
        if (foundUser != null)
            throw new RuntimeException("User already exists"); // TODO specific exception

        User response = userRepository.save(user);
        String jwtToken = jwtService.generateToken(response);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getUsername());
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(user);
            return new AuthenticationResponse(token);
        }

        throw new BadCredentialsException("Invalid username or password");
    }

    // VERIFICATION LOGIC
    public String sendVerificationCodeToEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user==null) throw new EntityNotFoundException();
        return user.getVerificationCode(); // TODO : what if expired? Should create new token?
    }

    @Transactional
    public void verify(String verificationCode) {
        User user = userRepository.findUserByVerificationCode(verificationCode); // TODO : make it service method

        // check if not user exists
        if (user==null) throw new EntityNotFoundException();

        // check if verification code is expired
        if (user.getVerificationCodeExpireDate().isBefore(ZonedDateTime.now()))
            throw new RuntimeException("verification code is expired"); // TODO specific exception

        user.setVerified(true);
        userRepository.save(user);
    }

    // RECOVERY LOGIC
    @Transactional
    public String createRecoveryCode(String userId){
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        String recoveryCode = UUID.randomUUID().toString();
        user.setRecoveryCode(recoveryCode);
        user.setRecoveryCodeExpireDate(ZonedDateTime.now().plusDays(1)); // TODO: fixed value?

        userRepository.save(user);
        return recoveryCode;
    }

    @Transactional
    public String recoverPasswordByGeneratingNew(String recoveryCode){
        User user = userRepository.findUserByRecoveryCode(recoveryCode);
        if (user==null) throw new EntityNotFoundException();

        // code duplication, TODO refactor?
        ZonedDateTime expireDate = user.getRecoveryCodeExpireDate();
        if (expireDate==null || expireDate.isBefore(ZonedDateTime.now()))
            throw new RuntimeException("verification code is expired"); // TODO specific exception

        String newPassword = UUID.randomUUID().toString().substring(0,18);
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setRecoveryCode(null); // remove code after using so that it can be used once
        user.setRecoveryCodeExpireDate(null);
        userRepository.save(user);

        return newPassword;
    }
}
