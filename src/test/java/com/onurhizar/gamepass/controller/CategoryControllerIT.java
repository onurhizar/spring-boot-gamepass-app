package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.abstracts.AbstractIntegrationTest;
import com.onurhizar.gamepass.model.request.CreateCategoryRequest;
import com.onurhizar.gamepass.model.request.UpdateCategoryRequest;
import com.onurhizar.gamepass.model.response.CategoryResponse;
import com.onurhizar.gamepass.util.AuthTokenHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryControllerIT extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthTokenHelper authTokenHelper;
    private static final String baseUrl = "/category";
    private static final String existingCategoryId = "66403305-972b-42b1-a71a-d7bb2828eebe";
    private static final String notExistingCategoryId = "aadceb23-d2ea-4432-aa7a-c71b4b15bcee";
    private static final String categoryIdToBeDeleted = "50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf4";

    @Test
    void whenGetRequest_thenStatus200(){
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void givenExistingGameId_whenGetGame_thenStatus200(){
        String url = baseUrl+"/"+existingCategoryId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void givenNotExistingGameId_whenGetUser_thenStatus404(){
        String url = baseUrl+"/"+notExistingCategoryId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void givenAdminToken_whenAddCategory_thenStatus200() {
        CreateCategoryRequest request = new CreateCategoryRequest("TestCategory");
        String url = baseUrl + "/" + existingCategoryId;
        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<CreateCategoryRequest> httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<CategoryResponse> response = restTemplate.postForEntity(url, httpEntity, CategoryResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void givenNoAdminToken_whenAddCategory_thenStatus403() {
        CreateCategoryRequest request = new CreateCategoryRequest("TestCategory");
        String url = baseUrl + "/" + existingCategoryId;
        HttpEntity<CreateCategoryRequest> httpEntity = new HttpEntity<>(request);
        ResponseEntity<CategoryResponse> response = restTemplate.postForEntity(url, httpEntity, CategoryResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void givenAdminToken_whenUpdateCategory_thenStatus200() {
        UpdateCategoryRequest request = new UpdateCategoryRequest("TestCategory", "GAME");
        String url = baseUrl + "/" + existingCategoryId;
        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<UpdateCategoryRequest> httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void givenNoAdminToken_whenUpdateCategory_thenStatus403() {
        UpdateCategoryRequest request = new UpdateCategoryRequest("TestCategory", "GAME");
        String url = baseUrl + "/" + existingCategoryId;
        HttpEntity<UpdateCategoryRequest> httpEntity = new HttpEntity<>(request);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void givenAdminToken_whenDeleteCategory_thenStatus200() {
        String url = baseUrl + "/" + categoryIdToBeDeleted;
        HttpHeaders headers = authTokenHelper.generateJwtHeader("admin@mail.com");
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
