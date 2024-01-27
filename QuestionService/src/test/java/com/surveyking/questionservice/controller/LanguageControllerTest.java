package com.surveyking.questionservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.surveyking.questionservice.model.entity.Language;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class LanguageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Container
    private final static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0.33");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    @Test
    void add() throws Exception {
        Language language = Language.builder()
                .code("ban")
                .name("Bangla")
                .build();
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/language/add")
                        //.with(SecurityMockMvcRequestPostProcessors.user("dummy").authorities(new SimpleGrantedAuthority("Language Info")))
                        .header("userId", "dummy")
                        .header("authorities", "Language Create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(language))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    private String asJsonString(Language language) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String json = objectMapper.writeValueAsString(language);
            return json;
        } catch (JsonProcessingException e) {
            return "";
        }

    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/language")
                        //.with(SecurityMockMvcRequestPostProcessors.user("dummy").authorities(new SimpleGrantedAuthority("Language Info")))
                        .header("userId", "dummy")
                        .header("authorities", "Language Info")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("English")));
    }
}