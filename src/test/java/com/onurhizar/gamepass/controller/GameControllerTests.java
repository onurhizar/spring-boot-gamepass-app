package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.abstracts.AbstractIntegrationTest;
import com.onurhizar.gamepass.model.request.CreateGameRequest;
import com.onurhizar.gamepass.model.response.GameResponse;
import com.onurhizar.gamepass.util.AuthTokenHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

public class GameControllerTests extends AbstractIntegrationTest {

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

    @Test
    void givenAdminToken_whenPostGame_thenStatus200() {
        CreateGameRequest request = new CreateGameRequest("test game title");
        String url = baseUrl;
        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<CreateGameRequest> httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<GameResponse> response = restTemplate.postForEntity(url, httpEntity, GameResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void givenAdminToken_whenUpdateGame_thenStatus200() {
        String url = baseUrl + "/" + existingGameId;
        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        CreateGameRequest request = new CreateGameRequest("updated game title");
        HttpEntity<CreateGameRequest> httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<GameResponse> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, GameResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    @DirtiesContext
    void givenAdminToken_whenDeleteGame_thenStatus200() {
        String url = baseUrl + "/" + existingGameId;
        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
}
