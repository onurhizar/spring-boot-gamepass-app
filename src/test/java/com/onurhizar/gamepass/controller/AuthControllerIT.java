package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.abstracts.AbstractIntegrationTest;
import com.onurhizar.gamepass.model.request.auth.RegisterRequest;
import com.onurhizar.gamepass.model.response.AuthenticationResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthControllerIT extends AbstractIntegrationTest {

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

}
