package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.abstracts.AbstractIntegrationTest;
import com.onurhizar.gamepass.model.request.auth.LoginRequest;
import com.onurhizar.gamepass.model.request.auth.RegisterRequest;
import com.onurhizar.gamepass.model.response.AuthenticationResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerIT extends AbstractIntegrationTest {

    private static String verificationCode;
    private static String recoveryCode;
    private final static String testingUserMail = "guest@mail.com";
    private final static String testingUserId = "102b8078-276a-49e2-b1df-ad41415e32b9";

    @BeforeAll
    void getCodes(){
        // get verification code
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/verify/email?email="+testingUserMail, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).contains("verify?code=");
        verificationCode = response.getBody().split("=")[1];

        // get recovery code
        response = restTemplate.getForEntity(
                "/recover/email/"+testingUserId, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).contains("recover?code=");
        recoveryCode = response.getBody().split("=")[1];
    }

    @Test
    void whenRegisterRequestWithValidBody_thenStatus200(){
        RegisterRequest request = new RegisterRequest("test","user",
                "integration@test.com","123456");
        HttpEntity<RegisterRequest> requestEntity = new HttpEntity<>(request);

        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
                "/register", requestEntity, AuthenticationResponse.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void whenRegisterRequestWithInvalidBody_thenStatus400(){
        RegisterRequest request = new RegisterRequest("test","user",
                "nonValidEmail","123456");

        ResponseEntity<Void> response = restTemplate.postForEntity(
                "/register", request, Void.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenLoginRequestWithValidCredentials_thenStatus200() {
        // given
        LoginRequest request = new LoginRequest("admin@mail.com", "123456");
        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(request);

        // when
        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
                "/login", requestEntity, AuthenticationResponse.class);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void whenLoginRequestWithInvalidCredentials_thenStatus401() {
        // given
        LoginRequest request = new LoginRequest("integration@test.com", "wrongpassword");
        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(request);

        // when
        ResponseEntity<Void> response = restTemplate.postForEntity(
                "/login", requestEntity, Void.class);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void whenVerifyRequestWithValidCode_thenStatus200() {
        // given
        String code = verificationCode;

        // when
        ResponseEntity<Void> response = restTemplate.getForEntity(
                "/verify?code=" + code, Void.class);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void whenVerifyRequestWithInvalidCode_thenStatus404() {
        // given
        String code = "invalid-code";

        // when
        ResponseEntity<Void> response = restTemplate.getForEntity(
                "/verify?code=" + code, Void.class);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void whenSendVerificationCodeToEmailRequest_thenStatus200() {
        // given
        String email = "guest@mail.com";

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/verify/email?email=" + email, String.class);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).contains("/verify?code=");
    }

    @Test
    void whenRecoverPasswordRequestWithValidCode_thenStatus200() {
        // given
        String code = recoveryCode;

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/recover?code=" + code, String.class);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).contains("Your new password is ");
    }

    @Test
    void whenRecoverPasswordRequestWithInvalidCode_thenStatus404() {
        // given
        String code = "invalid-code";

        // when
        ResponseEntity<Void> response = restTemplate.getForEntity(
                "/recover?code=" + code, Void.class);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void whenSendRecoveryCodeToEmailRequest_thenStatus200() {
        // given
        String userId = testingUserId;

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/recover/email/" + userId, String.class);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).contains("/recover?code=");
    }

}
