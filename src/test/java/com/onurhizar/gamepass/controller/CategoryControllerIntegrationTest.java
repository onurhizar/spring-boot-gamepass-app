package com.onurhizar.gamepass.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onurhizar.gamepass.abstracts.AbstractIntegrationTest;
import com.onurhizar.gamepass.model.request.CreateCategoryRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // TODO : do not use mockMvc in integration tests, use TestRestTemplate

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - executes before each test method in this class");
    }

    @Test
    public void whenSendingGet_thenCategoriesReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/category"))
                .andExpect(status().isOk())
                .andDo(a -> log.info(a.getResponse().getContentAsString()))
                .andExpect(jsonPath("$[0].name").value("GAME"))
                .andExpect(jsonPath("$[0].games[0]").value("Portal 3"))
                .andExpect(jsonPath("$[0].games[1]").value("NFS Most Wanted"));
    }

    @Test
    void whenSendingPost_thenStatusIsOk() throws Exception{
        String parentCategoryId = "10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e";

        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
        createCategoryRequest.setName("Test Category");

        String json = mapper.writeValueAsString(createCategoryRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/category/"+parentCategoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.getBytes()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
