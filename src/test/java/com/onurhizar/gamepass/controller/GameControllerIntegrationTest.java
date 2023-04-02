package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.response.GameResponse;
import com.onurhizar.gamepass.service.GameService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class GameControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GameService gameService;

    @Test
    @Transactional
    public void givenGameList_whenListGamesEndpointCalled_thenRetrievesGameList() {
        // Given
        List<GameResponse> expectedGameList = gameService.listGames();

        // When
        ResponseEntity<List<GameResponse>> responseEntity = restTemplate.exchange(
                "/game",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GameResponse>>() {}
        );

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<GameResponse> actualGameList = responseEntity.getBody();
        assertThat(actualGameList).isEqualTo(expectedGameList);
    }
}
