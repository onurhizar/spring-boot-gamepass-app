package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.abstracts.AbstractIntegrationTest;
import com.onurhizar.gamepass.model.request.CreateUserRequest;
import com.onurhizar.gamepass.util.AuthTokenHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

public class GameControllerIT extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthTokenHelper authTokenHelper;
    private static String baseUrl = "/game";
    private static String existingGameId = "b4dceb23-d2ea-4432-aa7a-c71b4b15bcee";
    private static String notExistingGameId = "aadceb23-d2ea-4432-aa7a-c71b4b15bcee";

    @Test
    void whenGetRequest_thenStatus200(){
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void givenExistingGameId_whenGetGame_thenStatus200(){
        String url = baseUrl+"/"+existingGameId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenNotExistingGameId_whenGetUser_thenStatus404(){
        String url = baseUrl+"/"+notExistingGameId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
