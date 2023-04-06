package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.GamePassApplication;
import com.onurhizar.gamepass.config.ContainersEnvironment;
import com.onurhizar.gamepass.model.request.CreateUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
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
    void whenEmptyRequestBody_thenStatus400(){
        CreateUserRequest requestDto = new CreateUserRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateUserRequest> request = new HttpEntity<>(requestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/user",
                HttpMethod.POST,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenValidRequestBody_thenStatus200(){
        CreateUserRequest requestDto = new CreateUserRequest();
        requestDto.setEmail("integration-test@mail.com");
        requestDto.setName("integration");
        requestDto.setSurname("test");
        requestDto.setPassword("123456");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateUserRequest> request = new HttpEntity<>(requestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/user",
                HttpMethod.POST,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



}
