package org.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.project.HelmesAppApplication;
import org.project.dtos.UserPreferenceCreateRequest;
import org.project.dtos.UserPreferenceResponse;
import org.project.dtos.UserPreferenceUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = HelmesAppApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class UserPreferenceRestControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void createUserPreference_ReturnOK()
            throws Exception {

        mvc.perform(post("/api/v1/user-preferences")
                        .content(objectMapper.writeValueAsString(UserPreferenceCreateRequest.builder().isTermAccepted(true).selectorIds(Set.of(2L)).name("Patrick").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Patrick")))
                .andExpect(jsonPath("$.selectors[0].id", is(2)));
    }

    @Test
    @Order(2)
    public void findByNameUserPreference_ReturnOK()
            throws Exception {

        MvcResult mvcResult = mvc.perform(post("/api/v1/user-preferences")
                        .content(objectMapper.writeValueAsString(UserPreferenceCreateRequest.builder().isTermAccepted(true).selectorIds(Set.of(5L)).name("Joe").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();


        mvc.perform(get(Objects.requireNonNull(mvcResult.getResponse().getRedirectedUrl()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Joe")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.selectors[0].id", is(5)));
    }

    @Test

    @Order(3)
    public void findByIdPreference_ReturnOK()
            throws Exception {

        mvc.perform(get("/api/v1/user-preferences/id/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Joe")))
                .andExpect(jsonPath("$.selectors[0].id", is(5)));
    }


    @Test

    @Order(4)
    public void updateUserPreference_ReturnOK()
            throws Exception {

        MvcResult result = mvc.perform(post("/api/v1/user-preferences")
                        .content(objectMapper.writeValueAsString(UserPreferenceCreateRequest.builder().selectorIds(Set.of(1L)).isTermAccepted(true).name("Meera").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();


        MvcResult getResult = mvc.perform(get(Objects.requireNonNull(result.getResponse().getRedirectedUrl()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        UserPreferenceResponse userPreferenceResponse = objectMapper.readValue(getResult.getResponse().getContentAsString(), UserPreferenceResponse.class);

        mvc.perform(put("/api/v1/user-preferences/id/{id}", userPreferenceResponse.getId())
                        .content(objectMapper.writeValueAsString(UserPreferenceUpdateRequest.builder().selectorIds(Set.of(30L)).name("Meera2").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Meera2")))
                .andExpect(jsonPath("$.id", equalTo(userPreferenceResponse.getId()), Long.class))
                .andExpect(jsonPath("$.selectors[0].id", is(30L), Long.class));
    }
}
