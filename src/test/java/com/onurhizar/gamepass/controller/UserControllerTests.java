package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.abstracts.AbstractIntegrationTest;
import com.onurhizar.gamepass.model.request.CreateUserRequest;
import com.onurhizar.gamepass.util.AuthTokenHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTests extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthTokenHelper authTokenHelper;

    @Test
    void givenAdminAuth_whenGetRequest_returnUsers(){
        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange("/user", HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenAdminAuthAndExistingUserId_whenGetUser_thenStatus200(){
        String userId = "ad1a1ddd-2f1c-4cc9-85ea-312dfc487bc9";
        String url = "/user/"+userId;
        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenAdminAuthNotExistingUserId_whenGetUser_thenStatus404(){
        String userId = "111a3d25-2b7a-4683-89ed-ac0e42cdc879";
        String url = "/user/"+userId;
        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void givenNoAuth_whenEmptyRequestPost_thenStatus403(){
        CreateUserRequest requestDto = new CreateUserRequest();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<CreateUserRequest> request = new HttpEntity<>(requestDto, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/user",request,String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void givenAdminAuth_whenEmptyRequestPost_thenStatus400(){
        CreateUserRequest requestDto = new CreateUserRequest();

        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<CreateUserRequest> request = new HttpEntity<>(requestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange("/user", HttpMethod.POST, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void givenNoAuth_whenValidRequestPost_thenStatus403(){
        CreateUserRequest requestDto = new CreateUserRequest();
        requestDto.setEmail("integration-test@mail.com");
        requestDto.setName("integration");
        requestDto.setSurname("test");
        requestDto.setPassword("123456");

        ResponseEntity<String> response = restTemplate.postForEntity("/user", requestDto, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void givenAdminAuth_whenValidRequestPost_thenStatus200(){
        CreateUserRequest requestDto = new CreateUserRequest();
        requestDto.setEmail("integration-test@mail.com");
        requestDto.setName("integration");
        requestDto.setSurname("test");
        requestDto.setPassword("123456");

        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<CreateUserRequest> request = new HttpEntity<>(requestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange("/user", HttpMethod.POST, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test // TODO make it status 400
    void givenAdminAuth_whenEmailExistsWhilePost_thenStatus500(){
        CreateUserRequest requestDto = new CreateUserRequest();
        requestDto.setEmail("admin@mail.com");
        requestDto.setName("integration");
        requestDto.setSurname("test");
        requestDto.setPassword("123456");

        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange("/user", HttpMethod.POST, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @DirtiesContext
    void givenAdminAuth_whenDeleteUser_thenStatus200(){
        String userId = "102b8078-276a-49e2-b1df-ad41415e32b9";
        String url = "/user/"+userId;
        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenNoAuth_whenDeleteUser_thenStatus401Forbidden(){
        String userId = "102b8078-276a-49e2-b1df-ad41415e32b9";
        String url = "/user/"+userId;
        HttpEntity<String> request = new HttpEntity<>(null);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}
