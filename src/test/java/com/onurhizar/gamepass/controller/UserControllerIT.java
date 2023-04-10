package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.config.ContainersEnvironment;
import com.onurhizar.gamepass.model.request.CreateUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Slf4j
public class UserControllerIT extends ContainersEnvironment {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenGetRequest_returnUsers(){
        ResponseEntity<String> response = restTemplate.getForEntity("/user", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenExistingUserId_whenGetUser_thenStatus200(){
        String userId = "5b8a3d25-2b7a-4683-89ed-ac0e42cdc879";
        String url = "/user/"+userId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        log.info(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenNotExistingUserId_whenGetUser_thenStatus404(){
        String userId = "111a3d25-2b7a-4683-89ed-ac0e42cdc879";
        String url = "/user/"+userId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void whenEmptyRequestPost_thenStatus400(){
        CreateUserRequest requestDto = new CreateUserRequest();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<CreateUserRequest> request = new HttpEntity<>(requestDto, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/user",request,String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenValidRequestPost_thenStatus200(){
        CreateUserRequest requestDto = new CreateUserRequest();
        requestDto.setEmail("integration-test@mail.com");
        requestDto.setName("integration");
        requestDto.setSurname("test");
        requestDto.setPassword("123456");

        ResponseEntity<String> response = restTemplate.postForEntity("/user", requestDto, String.class);
        log.info(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test // TODO make it status 400
    void whenEmailExistsWhilePost_thenStatus500(){
        CreateUserRequest requestDto = new CreateUserRequest();
        requestDto.setEmail("admin@mail.com");
        requestDto.setName("integration");
        requestDto.setSurname("test");
        requestDto.setPassword("123456");

        HttpEntity<CreateUserRequest> request = new HttpEntity<>(requestDto);
        ResponseEntity<String> response = restTemplate.postForEntity("/user", request, String.class);

        log.info(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
