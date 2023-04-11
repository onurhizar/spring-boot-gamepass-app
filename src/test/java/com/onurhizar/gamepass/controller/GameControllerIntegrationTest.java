package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.abstracts.AbstractIntegrationTest;
import com.onurhizar.gamepass.model.response.GameResponse;
import com.onurhizar.gamepass.service.GameService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GameControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private GameService gameService;

    @Test
    @Transactional // TODO : make a repository method to get Categories of Games
    public void givenGameList_whenListGamesEndpointCalled_thenRetrievesGameList() {
        // Given
        List<GameResponse> expectedGameList = gameService.listGames();

        // When
        ResponseEntity<List<GameResponse>> responseEntity = restTemplate.exchange(
                "/game",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<GameResponse> actualGameList = responseEntity.getBody();
        assertThat(actualGameList).isEqualTo(expectedGameList);
    }
}
