package com.surveyking.questionservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class LanguageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void add() {
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