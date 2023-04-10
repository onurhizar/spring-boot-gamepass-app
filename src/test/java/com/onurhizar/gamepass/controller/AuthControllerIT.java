package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.abstracts.AbstractIntegrationTest;
import com.onurhizar.gamepass.model.request.auth.RegisterRequest;
import com.onurhizar.gamepass.model.response.AuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Slf4j
public class AuthControllerIT extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenRegisterRequestWithValidBody_thenStatus200(){
        RegisterRequest request = new RegisterRequest("test","user",
                "integration@test.com","123456");
        HttpEntity<RegisterRequest> requestEntity = new HttpEntity<>(request);

        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
                "/register", requestEntity, AuthenticationResponse.class);

        log.warn(String.valueOf(response.getBody()));
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        log.warn(String.valueOf(response.getBody().getToken()));
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
